package org.example.database;

import org.example.dao.*;

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
