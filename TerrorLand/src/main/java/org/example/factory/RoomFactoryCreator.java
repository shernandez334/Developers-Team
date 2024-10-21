package org.example.factory;

import org.example.entities.Room;
import org.example.enums.Difficulty;
import org.example.util.IOHelper;
import org.example.util.MenuHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.example.mysql.MySqlHelper.getConnection;

public class RoomFactoryCreator implements RoomFactory{
    private static final Logger LOGGER = LoggerFactory.getLogger(RoomFactoryCreator.class);

    public Room createElementRoom(){
        int room_id = getCurrentRoomId();

        String nameRoom = IOHelper.readString("Name of the room: ");
        Difficulty difficulty = MenuHelper.readDifficultySelection("Choose a level of difficulty: ");
        return new Room(room_id, nameRoom, difficulty);
    }

    public int getCurrentRoomId(){
        int currentRoomId = 0;
        String sql = "SELECT COALESCE(MAX(room_id), 1) AS next_id FROM room";
        try (Connection conn = getConnection("escape_room");
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            rs.next();
            currentRoomId = rs.getInt("next_id");
        } catch (SQLException e) {
            LOGGER.error("Couldn't get the next element_id number: {}", e.getMessage());
        }
        return currentRoomId;
    }
}
