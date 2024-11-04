package org.example.database;

import org.example.mysql.MySqlHelper;
import org.example.enums.SystemProperty;
import org.example.exceptions.ExecuteScriptIOException;
import org.example.exceptions.MySqlNotValidCredentialsException;

import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;

import static org.example.mysql.MySqlHelper.executeSqlFile;

public class DbInitialSetupMySql implements DbInitialSetup {

    @Override
    public void createDatabase() {
        try {
            executeSqlFile(SystemProperty.SQL_SCHEMA_CREATION_FILE.getValue());
        } catch (ExecuteScriptIOException ex) {
            System.out.println(ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    @Override
    public boolean checkDatabaseMissing() throws SQLException, MySqlNotValidCredentialsException {
        try (Connection ignored = MySqlHelper.getConnection(SystemProperty.DB_NAME.getValue())){
            return false;
        } catch (SQLException e) {
            if (e.getErrorCode() == 1049) {
                return true;
            }
            if (e.getErrorCode() == 1045) {
                throw new MySqlNotValidCredentialsException(String.format(
                        "Connection to MySQL rejected: modify the credentials at '%s'.%n",
                        Path.of(SystemProperty.PROPERTIES_FILE_PATH.getValue()).toAbsolutePath()));
            } else {
                throw new SQLException("Error connecting to the database. Check if the service is running.");
            }
        }
    }


}
