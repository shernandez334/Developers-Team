package org.example.dao;

import org.example.entities.Notification;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import static org.example.mysql.MySqlHelper.*;

public class NotificationDaoMySql implements NotificationDao {

    @Override
    public List<Integer> getSubscribersIds() {
        return retrieveSingleColumnFromDatabase("SELECT user_id FROM subscription;", Integer.class);
    }

    @Override
    public Notification saveNotification(Notification notification){
        String message = notification.getMessage().replaceAll("'", "''");
        String sql = String.format("INSERT INTO notification (user_id, message) VALUES (%d, '%s');",
                notification.getUserId(), message);
        try {
            int id = executeInsertStatementAndGetId(sql);
            notification.setId(id);
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new RuntimeException(e);
        }
        return notification;
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
