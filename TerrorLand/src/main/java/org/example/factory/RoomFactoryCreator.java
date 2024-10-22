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
    private static final DatabaseInputFactorySQL DATABASEINPUT = new DatabaseInputFactorySQL();

    public Room createElementRoom(){
        String name = IOHelper.readString("Name of the room: ");
        Difficulty difficulty = MenuHelper.readDifficultySelection("Choose a level of difficulty: ");
        DATABASEINPUT.inputRoomIntoTable(name, difficulty, 0);
        int room_id = getCurrentRoomId();
        return new Room(room_id, name, difficulty, 0);
    }

    public int getCurrentRoomId() {
        int currentRoomId = 0;
        String sql = "SELECT room_id FROM room ORDER BY room_id DESC LIMIT 1";
        try (Connection conn = getConnection("escape_room");
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            rs.next();
            currentRoomId = rs.getInt("room_id");
        } catch (SQLException e) {
            LOGGER.error("Couldn't get the next element_id number: {}", e.getMessage());
        }
        return currentRoomId;
    }
}
