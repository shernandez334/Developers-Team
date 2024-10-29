package org.example.dao;

import org.example.entities.Attempt;
import org.example.entities.Player;
import org.example.entities.Room;
import org.example.entities.UserPlaysRoomDto;
import org.example.mysql.MySqlHelper;


import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static org.example.mysql.MySqlHelper.retrieveMultipleColumnsFromDatabase;

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

    @Override
    public UserPlaysRoomDto getPlays(Player player) {
        UserPlaysRoomDto response = new UserPlaysRoomDto(player);
        List<List<Object>> items = retrieveMultipleColumnsFromDatabase(
                String.format("SELECT room_id, solved, date FROM user_plays_room WHERE user_id = %d;", player.getId()),
                new String[] {Integer.class.getName(), Integer.class.getName(), LocalDateTime.class.getName()});
        items.forEach(e -> response.addAttempt((Integer) e.getFirst(),
                new Attempt((Integer) e.get(1) == 1, (LocalDateTime) e.get(2))));
        return response;
    }

}