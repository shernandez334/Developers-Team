package org.example.services;

import org.example.database.DatabaseFactory;
import org.example.database.FactoryProvider;
import org.example.enums.SystemProperty;
import org.example.enums.ConfigurableProperty;
import org.example.enums.ProcessStatus;
import org.example.exceptions.MySqlNotValidCredentialsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;

public class InitializeResourcesService {
    private static final Logger log = LoggerFactory.getLogger(InitializeResourcesService.class);


    public ProcessStatus runSetup(){
        System.out.println("Running initial setup... ");

        if (propertiesFileSetup() == ProcessStatus.EXIT) return ProcessStatus.EXIT;

        DatabaseFactory databaseFactory = FactoryProvider.getInstance().getDbFactory();
        if (databaseSetup(databaseFactory) == ProcessStatus.EXIT) return ProcessStatus.EXIT;

        System.out.println("... all systems online.");
        return ProcessStatus.CONTINUE;
    }

    private ProcessStatus propertiesFileSetup() {
        if (checkPropertiesFileMissing()){
            try {
                createPropertiesFile();
                System.out.println("Created properties.properties file...");
                System.out.println("Edit the settings at 'properties.properties' and rerun the app.");
                log.info("Created the 'properties.properties' file.");
                return ProcessStatus.EXIT;
            } catch (IOException e) {
                System.out.println("The setup process has been interrupted.");
                log.error("Could not create the 'properties.properties' file.");
                return ProcessStatus.EXIT;
            }
        }
        return ProcessStatus.CONTINUE;
    }

    public boolean checkPropertiesFileMissing(){
        final Path path = Path.of(SystemProperty.PROPERTIES_FILE_PATH.getValue());
        return !Files.exists(path);
    }

    public void createPropertiesFile() throws IOException {
        final Path path = Path.of(SystemProperty.PROPERTIES_FILE_PATH.getValue());
        String PropertiesFileContent =
                ConfigurableProperty.PROVIDER.getValue() + "=" + SystemProperty.DEFAULT_PROVIDER.getValue() + "\n" +
                ConfigurableProperty.URL.getValue() + "=" + SystemProperty.DEFAULT_DB_URL.getValue() + "\n" +
                ConfigurableProperty.USER.getValue() + "=" + SystemProperty.DEFAULT_DB_USER.getValue() + "\n" +
                ConfigurableProperty.PASSWORD.getValue() + "=" + SystemProperty.DEFAULT_DB_PASSWORD.getValue() + "\n" +
                "\n" +
                ConfigurableProperty.PLAY_TIME.getValue() + "=" + SystemProperty.DEFAULT_PLAY_TIME.getValue() ;
        Files.createFile(path);
        Files.writeString(path, PropertiesFileContent);
    }

    private ProcessStatus databaseSetup(DatabaseFactory databaseFactory) {
        try {
            if (checkDatabaseMissing(databaseFactory)){
                createDatabase(databaseFactory);
                System.out.println("Database created successfully.");
                log.info("Database created successfully.");
            }
        } catch (MySqlNotValidCredentialsException e) {
            System.out.println(e.getMessage());
            log.warn("Connection to MySQL rejected: wrong credentials.");
            return ProcessStatus.EXIT;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            log.error(e.getMessage());
            return ProcessStatus.EXIT;
        }
        return ProcessStatus.CONTINUE;
    }

    private boolean checkDatabaseMissing(DatabaseFactory databaseFactory) throws SQLException, MySqlNotValidCredentialsException {
        return databaseFactory.createDbInitialSetup().checkDatabaseMissing();
    }

    private void createDatabase(DatabaseFactory databaseFactory) {
        databaseFactory.createDbInitialSetup().createDatabase();
    }

    public void initiateRewardsService(DatabaseFactory databaseFactory) {
        RewardsService.getInstance().setChainOfResponsibility(databaseFactory);
    }
}
