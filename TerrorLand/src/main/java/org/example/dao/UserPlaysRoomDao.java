package org.example.dao;

import org.example.entities.Player;
import org.example.entities.Room;
import org.example.dto.UserPlaysRoomDto;
import org.example.enums.Difficulty;

public interface UserPlaysRoomDao {
    void savePlay(Player player, Room room, boolean solved);

    UserPlaysRoomDto getPlays(Player player);

    int getTotalPlays(Player player);

    int getTotalPlays(Player player, Difficulty difficulty);

    int getTotalPlays(Player player, Difficulty difficulty, boolean solved);

    int getSuccessfulPlays(Player player, int last);

    int getSuccessfulPlays(Player player, int roomId, int last);

}
