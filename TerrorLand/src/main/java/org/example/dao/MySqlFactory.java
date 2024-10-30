package org.example.dao;

import org.example.database.DbInitialSetup;
import org.example.database.DbInitialSetupMySql;

public class MySqlFactory implements DatabaseFactory {

    private static DatabaseFactory instance;

    private MySqlFactory(){
    }

    public static DatabaseFactory getInstance(){
        if (instance == null){
            instance = new MySqlFactory();
        }
        return instance;
    }

    @Override
    public UserDao createUserDao() {
        return new UserDaoMySql();
    }

    @Override
    public TicketDao createTicketDao() {
        return new TicketDaoMySql();
    }

    @Override
    public PlayerDao createPlayerDao() {
        return new PlayerDaoMySql();
    }

    @Override
    public NotificationDao createNotificationDao() {
        return new NotificationDaoMySql();
    }

    @Override
    public DbInitialSetup createDbInitialSetup() {
        return new DbInitialSetupMySql();
    }

    @Override
    public PropertiesDao createPropertiesDao() {
        return new PropertiesDaoMySql();
    }

    @Override
    public UserPlaysRoomDao createUserPlaysRoomDao() {
        return new UserPlaysRoomDaoMySql();
    }

    @Override
    public RewardDao createRewardDao() {
        return new RewardDaoMySql();
    }
}
