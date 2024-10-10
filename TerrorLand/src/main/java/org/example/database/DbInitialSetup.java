package org.example.database;

import org.example.exceptions.MySqlNotValidCredentialsException;

import java.sql.SQLException;

public interface DbInitialSetup {

    void createIfMissing() throws MySqlNotValidCredentialsException, SQLException;

}
