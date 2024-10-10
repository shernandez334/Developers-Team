package org.example.entities;

import org.example.dao.NotificationDaoMySql;

public class NotificationEntity {
    private int id;
    private final int userId;
    private final String message;

    public NotificationEntity(int userId, String message) {
        this.id = 0;
        this.userId = userId;
        this.message = message;
    }

    public NotificationEntity(int id, int userId, String message) {
        this.id = id;
        this.userId = userId;
        this.message = message;
    }

    public int getId() {
        return this.id;
    }

    public int getUserId() {
        return this.userId;
    }

    public String getMessage() {
        return this.message;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void delete(){
        new NotificationDaoMySql().deleteNotification(this);
    }


}
