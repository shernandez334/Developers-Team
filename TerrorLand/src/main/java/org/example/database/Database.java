package org.example.database;

import org.example.enums.Difficulty;
import org.example.exceptions.MySqlCredentialsException;


public interface Database {

    public void createIfMissing() throws MySqlCredentialsException;

    public void execute(Element e);
}
