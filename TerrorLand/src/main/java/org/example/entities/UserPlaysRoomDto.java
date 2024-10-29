package org.example.entities;

import java.time.LocalDateTime;
import java.util.*;

//TODO use for certificates or delete
public class UserPlaysRoomDto {

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
