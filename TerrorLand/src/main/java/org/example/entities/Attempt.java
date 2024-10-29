package org.example.entities;

import java.time.LocalDateTime;

public class Attempt {

    private boolean success;
    private LocalDateTime dateTime;

    public Attempt(boolean success, LocalDateTime dateTime) {
        this.success = success;
        this.dateTime = dateTime;
    }

    public boolean isSuccess() {
        return success;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

}
