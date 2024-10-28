package org.example.dao;

import org.example.entities.Player;
import org.example.entities.Room;

public interface UserPlaysRoomDao {
    void savePlay(Player player, Room room, boolean solved);

}
