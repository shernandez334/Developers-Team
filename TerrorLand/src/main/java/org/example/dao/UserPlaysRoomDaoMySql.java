package org.example.dao;

import org.example.dto.AttemptDTO;
import org.example.entities.Player;
import org.example.entities.Room;
import org.example.dto.UserPlaysRoomDto;
import org.example.enums.Difficulty;
import org.example.exceptions.MySqlEmptyResultSetException;
import org.example.mysql.MySqlHelper;


import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;
import java.util.List;

import static org.example.mysql.MySqlHelper.retrieveMultipleColumnsFromDatabase;
import static org.example.mysql.MySqlHelper.retrieveSingleValueFromDatabase;

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
                String.format("SELECT room_id, solved, date, difficulty " +
                        "FROM user_plays_room JOIN room USING (room_id) " +
                        "WHERE user_id = %d;", player.getId()),
                new String[] {Integer.class.getName(), Integer.class.getName(), LocalDateTime.class.getName(),
                String.class.getName()});
        items.forEach(e -> response.addAttempt((Integer) e.getFirst(),
                new AttemptDTO((Integer) e.get(1) == 1, (LocalDateTime) e.get(2),
                        Difficulty.valueOf((String) e.get(3)))));
        return response;
    }

    @Override
    public int getTotalPlays(Player player) {
        String sql = String.format("SELECT COUNT(solved) FROM user_plays_room WHERE user_id = %d;", player.getId());
        try {
            Long totalPlays = retrieveSingleValueFromDatabase(sql, Long.class);
            if (totalPlays == null) return 0;
            return totalPlays.intValue();
        } catch (MySqlEmptyResultSetException e) {
            return 0;
        }
    }

    @Override
    public int getTotalPlays(Player player, Difficulty difficulty) {
        String sql = String.format("SELECT COUNT(solved) " +
                "FROM user_plays_room JOIN room USING (room_id) " +
                "WHERE user_id = %d AND difficulty = '%s';", player.getId(), difficulty.toString());
        try {
            Long totalPlays = retrieveSingleValueFromDatabase(sql, Long.class);
            if (totalPlays == null) return 0;
            return totalPlays.intValue();
        } catch (MySqlEmptyResultSetException e) {
            return 0;
        }
    }

    @Override
    public int getTotalPlays(Player player, Difficulty difficulty, boolean solved) {
        String sql = String.format("SELECT COUNT(solved) " +
                "FROM user_plays_room JOIN room USING (room_id) " +
                "WHERE user_id = %d AND difficulty = '%s' AND solved = %d;",
                player.getId(), difficulty.toString(), solved ? 1 : 0);
        try {
            Long totalPlays = retrieveSingleValueFromDatabase(sql, Long.class);
            if (totalPlays == null) return 0;
            return totalPlays.intValue();
        } catch (MySqlEmptyResultSetException e) {
            return 0;
        }
    }

    @Override
    public int getSuccessfulPlays(Player player, int last) {
        String sql = String.format("SELECT COUNT(*) FROM (" +
                        "SELECT * FROM user_plays_room WHERE user_id = %d " +
                        "ORDER BY date DESC LIMIT %d) AS last " +
                        "GROUP BY solved HAVING solved = 1;",
                player.getId(), last);
        try {
            Long successfulPlays = retrieveSingleValueFromDatabase(sql, Long.class);
            if (successfulPlays == null) return 0;
            return successfulPlays.intValue();
        } catch (MySqlEmptyResultSetException e) {
            return 0;
        }
    }

    @Override
    public int getSuccessfulPlays(Player player, int roomId, int last) {
        String sql = String.format("SELECT COUNT(*) FROM (" +
                        "SELECT * FROM user_plays_room WHERE user_id = %d AND room_id = %d " +
                        "ORDER BY date DESC LIMIT %d) AS last " +
                        "GROUP BY solved HAVING solved = 1;",
                player.getId(), roomId, last);
        try {
            Long successfulPlays = retrieveSingleValueFromDatabase(sql, Long.class);
            if (successfulPlays == null) return 0;
            return successfulPlays.intValue();
        } catch (MySqlEmptyResultSetException e) {
            return 0;
        }
    }

}