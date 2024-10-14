package org.example.database;

import org.example.exceptions.MySqlNotValidCredentialsException;

import java.sql.SQLException;

public interface DbInitialSetup {

    boolean checkDatabaseMissing() throws SQLException, MySqlNotValidCredentialsException;

    //TODO revise the exceptions thrown by this method
    void createDatabase();

}
