package org.example.database;

import org.example.exceptions.MySqlCredentialsException;
import org.example.persistance.Element;

public interface Database {

    public void createIfMissing() throws MySqlCredentialsException;
}
