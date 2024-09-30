package org.example.logic;

public class Notification implements Storable{
    int userId;
    String message;

    public Notification(int userId, String message) {
        this.userId = userId;
        this.message = message;
    }

    @Override
    public String insertString() {
        return String.format("INSERT INTO notification (user_id, message) VALUES (%d, '%s');", this.userId, this.message);
    }
}
