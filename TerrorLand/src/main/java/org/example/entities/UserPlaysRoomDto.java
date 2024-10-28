package org.example.entities;

import java.time.LocalDateTime;
import java.util.*;

//TODO use for certificates or delete
public class UserPlaysRoomDto {

    public static class Attempt{
        private boolean success;
        private LocalDateTime timestamp;

        public Attempt(boolean success, LocalDateTime timestamp) {
            this.success = success;
            this.timestamp = timestamp;
        }

        public boolean isSuccess() {
            return success;
        }

        public LocalDateTime getTimestamp() {
            return timestamp;
        }
    }

    Player player;

    Map<Integer, List<Attempt>> attempts;

    public UserPlaysRoomDto(Player player) {
        this.player = player;
        this.attempts = new HashMap<>();
    }

    public void addAttempt(int roomId, Attempt attempt) {
        List<Attempt> attempts= this.attempts.get(roomId);
        if (attempts != null){
            attempts.add(attempt);
        }else {
            attempts = new ArrayList<>();
            attempts.add(attempt);
            this.attempts.put(roomId, attempts);
        }
    }

    public Map<Integer, List<Attempt>> getAttempts() {
        return attempts;
    }
}
