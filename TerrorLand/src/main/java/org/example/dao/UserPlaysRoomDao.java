package org.example.dao;

import org.example.entities.Player;
import org.example.entities.Room;
import org.example.entities.UserPlaysRoomDto;

public interface UserPlaysRoomDao {
    void savePlay(Player player, Room room, boolean solved);

    UserPlaysRoomDto getPlays(Player player);

}
