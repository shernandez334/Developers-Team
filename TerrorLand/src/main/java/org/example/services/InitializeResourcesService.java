package org.example.services;

import org.example.database.DbInitialSetupMySql;
import org.example.enums.DefaultProperties;
import org.example.exceptions.MySqlNotValidCredentialsException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;

public class InitializeResourcesService {

    public void run() throws MySqlNotValidCredentialsException, SQLException {
        System.out.print("Running initial setup... ");
        createPropertiesFileIfMissing();
        new DbInitialSetupMySql().createIfMissing();
        System.out.println("... all systems online.");
    }

    public static void createPropertiesFileIfMissing(){
        final Path path = Path.of(DefaultProperties.PROPERTIES_FILE_PATH.getValue());
        if (!Files.exists(path)) {
            try {
                String defaultInfo = "db.provider=MySQL" +
                        "\ndb.url=" + DefaultProperties.DEFAULT_DB_URL.getValue() +
                        "\ndb.user=" + DefaultProperties.DEFAULT_DB_USER.getValue() +
                        "\ndb.password=" + DefaultProperties.DEFAULT_DB_PASSWORD.getValue();
                Files.createFile(path);
                Files.writeString(path, defaultInfo);
                System.out.println("Created properties.properties file.");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}