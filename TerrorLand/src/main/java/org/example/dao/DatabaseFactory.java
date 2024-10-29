package org.example.dao;

import org.example.database.DbInitialSetup;
import org.example.enums.ConfigurableProperty;
import org.example.enums.Properties;

public interface DatabaseFactory {

    UserDao createUserDao();
    TicketDao createTicketDao();
    PlayerDao createPlayerDao();
    NotificationDao createNotificationDao();
    DbInitialSetup createDbInitialSetup();
    PropertiesDao createPropertiesDao();
    UserPlaysRoomDao createUserPlaysRoomDao();

    /**
     * @deprecated
     * Use FactoryProvider.getInstance().getFactory() instead.
     */
    @Deprecated
    public static DatabaseFactory get(){
        if (Properties.getProperty(ConfigurableProperty.PROVIDER).equalsIgnoreCase("mysql")){
            return MySqlFactory.getInstance();
        }else {
            System.out.printf("'%s' is not valid as a database provider. Fix the properties file.",
                    ConfigurableProperty.PROVIDER);
            throw new RuntimeException();
        }
    }
}
