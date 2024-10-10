package org.example.dao;

import org.example.entities.NotificationEntity;
import org.example.entities.PlayerEntity;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import static org.example.dao.GenericMethodsMySQL.*;

public class NotificationDaoMySql {

    public List<Integer> getSubscribers() {
        return retrieveSingleColumnFromDatabase("SELECT user_id FROM subscription;", Integer.class);
    }

    public void storeNotification(NotificationEntity notification){
        String message = notification.getMessage().replaceAll("'", "''");
        String sql = String.format("INSERT INTO notification (user_id, message) VALUES (%d, '%s');",
                notification.getUserId(), message);
        try {
            createStatementAndExecute(sql);
            notification.setId(getLastInsertedId());
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteNotification(NotificationEntity notification) {
        String sql = String.format("DELETE FROM notification WHERE notification_id = %d;", notification.getId());
        try {
            createStatementAndExecute(sql);
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new RuntimeException(e);
        }
    }

}
