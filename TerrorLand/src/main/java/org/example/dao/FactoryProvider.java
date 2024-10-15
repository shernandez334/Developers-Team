package org.example.dao;

import org.example.enums.FileProps;
import org.example.enums.Properties;

public class FactoryProvider {

    private static FactoryProvider instance;

    private FactoryProvider(){
    }

    public static FactoryProvider getInstance(){
        if (instance == null){
            instance = new FactoryProvider();
        }
        return instance;
    }

    public DatabaseFactory getFactory(){
        if (Properties.getProperty(FileProps.PROVIDER.getValue()).equalsIgnoreCase("mysql")){
            return MySqlFactory.getInstance();
        }else {
            System.out.printf("'%s' is not valid as a database provider. Fix the properties file.",
                    FileProps.PROVIDER.getValue());
            throw new RuntimeException();
        }
    }
}
