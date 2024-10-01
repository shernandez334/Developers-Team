package org.example.model;

import org.example.database.Deletable;
import org.example.database.MySQL;
import org.example.database.Storable;

public class Notification implements Storable, Deletable {
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

    public int getId() {
        return this.id;
    }

    public int getUserId() {
        return this.userId;
    }

    public String getMessage() {
        return this.message;
    }

    public void deleteFromDatabase(){
        MySQL.deleteFromDatabase(this);
    }

    @Override
    public String insertQuery() {
        return String.format("INSERT INTO notification (user_id, message) VALUES (%d, '%s');",
                this.userId, this.message);
    }

    @Override
    public String deleteQuery() {
        return String.format("DELETE FROM notification WHERE notification_id = %d;", this.id);
    }
}
