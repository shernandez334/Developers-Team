package org.example.database;

import org.example.mySQL.MySqlHelper;
import org.example.enums.DefaultProperties;
import org.example.exceptions.ExecuteScriptIOException;
import org.example.exceptions.MySqlNotValidCredentialsException;

import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;

import static org.example.mySQL.MySqlHelper.executeSqlFile;

public class DbInitialSetupMySql implements DbInitialSetup {

    @Override
    public void createDatabaseIfMissing() throws MySqlNotValidCredentialsException, SQLException {
        try (Connection connection = MySqlHelper.getConnection(DefaultProperties.DB_NAME.getValue())){
            System.out.print("connected to the Database");
        } catch (SQLException e) {
            if (e.getErrorCode() == 1049) {
                System.out.println("Creating Database from scratch...");
                try {
                    executeSqlFile(DefaultProperties.SQL_SCHEMA_CREATION_FILE.getValue());
                } catch (ExecuteScriptIOException ex) {
                    System.out.println(ex.getMessage());
                    throw new RuntimeException(ex);
                }
            } else if (e.getErrorCode() == 1045) {
                throw new MySqlNotValidCredentialsException(String.format(
                        "Possible action: modify the credentials at '%s' if necessary.%n",
                        Path.of(DefaultProperties.PROPERTIES_FILE_PATH.getValue()).toAbsolutePath()));
            } else {
                throw new SQLException("Unexpected error, the database is unreachable.");
            }
        }
    }


}
