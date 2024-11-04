package org.example.dto;

import org.example.entities.Player;

import java.util.*;

public class UserPlaysRoomDto {

    Player player;

    Map<Integer, List<AttemptDTO>> attempts;

    public UserPlaysRoomDto(Player player) {
        this.player = player;
        this.attempts = new HashMap<>();
    }

    public void addAttempt(int roomId, AttemptDTO attemptDTO) {
        List<AttemptDTO> attempts= this.attempts.get(roomId);
        if (attempts != null){
            attempts.add(attemptDTO);
        }else {
            attempts = new ArrayList<>();
            attempts.add(attemptDTO);
            this.attempts.put(roomId, attempts);
        }
    }

    public Map<Integer, List<AttemptDTO>> getAttempts() {
        return attempts;
    }
}
