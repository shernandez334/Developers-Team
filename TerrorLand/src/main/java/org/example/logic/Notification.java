package org.example.logic;

public class Notification implements Storable, Deletable{
    private final int id;
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

    @Override
    public String insertString() {
        return String.format("INSERT INTO notification (user_id, message) VALUES (%d, '%s');",
                this.userId, this.message);
    }

    @Override
    public String deleteString() {
        return String.format("DELETE FROM notification WHERE notification_id = %d;", this.id);
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", userId=" + userId +
                ", message='" + message + '\'' +
                '}';
    }
}
