package org.example.entities;

import org.example.enums.Difficulty;

import java.time.LocalDateTime;

public class Attempt {

    private boolean success;
    private LocalDateTime dateTime;
    private Difficulty difficulty;

    public Attempt(boolean success, LocalDateTime dateTime, Difficulty difficulty) {
        this.success = success;
        this.dateTime = dateTime;
        this.difficulty = difficulty;
    }

    public boolean isSuccess() {
        return success;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

}
