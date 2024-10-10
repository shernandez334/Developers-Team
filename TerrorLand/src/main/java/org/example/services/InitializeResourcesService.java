package org.example.services;

import org.example.dao.DatabaseFactory;
import org.example.enums.DefaultProperties;
import org.example.enums.FileProps;
import org.example.exceptions.MySqlNotValidCredentialsException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;

public class InitializeResourcesService {

    public void run() throws MySqlNotValidCredentialsException, SQLException {
        System.out.print("Running initial setup... ");
        createPropertiesFileIfMissing();
        DatabaseFactory.get().createDbInitialSetup().createDatabaseIfMissing();
        System.out.println("... all systems online.");
    }

    public static void createPropertiesFileIfMissing(){
        final Path path = Path.of(DefaultProperties.PROPERTIES_FILE_PATH.getValue());
        if (!Files.exists(path)) {
            try {
                String defaultInfo =
                        FileProps.PROVIDER.getValue() + "=" + DefaultProperties.DEFAULT_PROVIDER.getValue() + "\n" +
                        FileProps.URL.getValue() + "=" + DefaultProperties.DEFAULT_DB_URL.getValue() + "\n" +
                        FileProps.USER.getValue() + "=" + DefaultProperties.DEFAULT_DB_USER.getValue() + "\n" +
                        FileProps.PASSWORD.getValue() + "=" + DefaultProperties.DEFAULT_DB_PASSWORD.getValue();
                Files.createFile(path);
                Files.writeString(path, defaultInfo);
                System.out.println("Created properties.properties file.");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
