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
    RewardDao createRewardDao();
}
