package org.example.dao;

import org.example.enums.ConfigurableProperty;
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

    public DatabaseFactory getDbFactory(){
        if (Properties.getProperty(ConfigurableProperty.PROVIDER).equalsIgnoreCase("mysql")){
            return MySqlFactory.getInstance();
        }else {
            System.out.printf("'%s' is not valid as a database provider. Fix the properties file.",
                    ConfigurableProperty.PROVIDER);
            throw new RuntimeException();
        }
    }
}
