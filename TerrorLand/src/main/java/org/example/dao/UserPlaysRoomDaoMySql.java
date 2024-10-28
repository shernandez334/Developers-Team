package org.example.dao;

import org.example.entities.Player;
import org.example.entities.Room;
import org.example.mysql.MySqlHelper;

import java.sql.*;

public class UserPlaysRoomDaoMySql implements UserPlaysRoomDao {

        @Override
        public void savePlay(Player player, Room room, boolean solved) {
            String sql = String.format("INSERT INTO user_plays_room (user_id, room_id, solved) VALUES (%d, %d, %d);",
                    player.getId(), room.getRoomId(), solved ? 1 : 0);
            try {
                MySqlHelper.createStatementAndExecute(sql);
            } catch (SQLIntegrityConstraintViolationException e) {
                throw new RuntimeException(e);
            }
        }

}