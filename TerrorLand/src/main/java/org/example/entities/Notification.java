package org.example.entities;

public class Notification {
    private int id;
    private final int userId;
    private final String message;

    public Notification(int userId, String message) {
        this.id = 0;
        this.userId = userId;
        this.message = message;
    }

    public Notification(int id, int userId, String message) {
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

}
