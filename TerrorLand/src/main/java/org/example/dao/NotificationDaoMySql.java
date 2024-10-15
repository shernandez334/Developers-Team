package org.example.dao;

import org.example.entities.Notification;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import static org.example.mysql.MySqlHelper.*;

public class NotificationDaoMySql implements NotificationDao {

    @Override
    public List<Integer> getSubscribers() {
        return retrieveSingleColumnFromDatabase("SELECT user_id FROM subscription;", Integer.class);
    }

    @Override
    public void storeNotification(Notification notification){
        String message = notification.getMessage().replaceAll("'", "''");
        String sql = String.format("INSERT INTO notification (user_id, message) VALUES (%d, '%s');",
                notification.getUserId(), message);
        try {
            createStatementAndExecute(sql);
            //notification.setId(getLastInsertedId());
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteNotification(Notification notification) {
        String sql = String.format("DELETE FROM notification WHERE notification_id = %d;", notification.getId());
        try {
            createStatementAndExecute(sql);
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new RuntimeException(e);
        }
    }

}
