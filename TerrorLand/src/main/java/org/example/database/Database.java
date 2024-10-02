package org.example.database;

import org.example.exceptions.MySqlCredentialsException;
import org.example.persistence.Element;

public interface Database {

    public void createIfMissing() throws MySqlCredentialsException;

    public void execute(Element e);
}
