package org.example.dao;

import org.example.enums.FileProps;
import org.example.enums.Properties;

public class FactoryProvider {

    public DatabaseFactory get(){
        if (Properties.getProperty(FileProps.PROVIDER.getValue()).equalsIgnoreCase("mysql")){
            return new MySqlFactory();
        }else {
            System.out.printf("'%s' is not valid as a database provider. Fix the properties file.",
                    FileProps.PROVIDER.getValue());
            throw new RuntimeException();
        }
    }
}
