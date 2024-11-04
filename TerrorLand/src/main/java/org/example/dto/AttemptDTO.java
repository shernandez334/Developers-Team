package org.example.dto;

import org.example.enums.Difficulty;

import java.time.LocalDateTime;

public class AttemptDTO {

    private boolean success;
    private LocalDateTime dateTime;
    private Difficulty difficulty;

    public AttemptDTO(boolean success, LocalDateTime dateTime, Difficulty difficulty) {
        this.success = success;
        this.dateTime = dateTime;
        this.difficulty = difficulty;
    }

    public boolean isSuccess() {
        return success;
    }

}
