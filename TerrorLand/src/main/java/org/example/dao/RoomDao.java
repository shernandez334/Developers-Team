package org.example.dao;

import org.example.entities.Room;
import org.example.enums.Difficulty;
import org.example.enums.RoomStatus;
import org.example.factory.RoomFactory;
import org.example.util.IOHelper;
import org.example.util.MenuHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static org.example.mysql.MySqlHelper.getConnection;


public class RoomDao implements RoomFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(RoomDao.class);
    private static final DisplayDao DISPLAY = new DisplayDao();
    private static final DatabaseInputDaoSQL DATABASEINPUT = new DatabaseInputDaoSQL();

    public Room createElementRoom(){
        String name = IOHelper.readString("Name of the room: ");
        Difficulty difficulty = MenuHelper.readDifficultySelection("Choose a level of difficulty: ");
        DATABASEINPUT.inputRoomIntoTable(name, difficulty, 0);
        int room_id = getCurrentRoomId();
        System.out.println(room_id);
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

    public void deleteRoom(){
        DISPLAY.displayAllRooms(RoomStatus.ACTIVE);
        int roomId = IOHelper.readInt("Enter the Room Id you want to delete: ");
        updateRoomStatus(roomId, 1);
    }

    public void retrieveRoom(){
        DISPLAY.displayAllRooms(RoomStatus.DELETED);
        int roomId = IOHelper.readInt("Enter the Room Id you want to delete: ");
        updateRoomStatus(roomId, 0);
    }

    public void updateRoomStatus(int roomId, int newRoomStatus){
        String updateQuery = "UPDATE room SET deleted = ? WHERE room_id = ?";
        try (Connection conn = getConnection("escape_room");
             PreparedStatement psmt = conn.prepareStatement(updateQuery)) {
            psmt.setInt(1, newRoomStatus);
            psmt.setInt(2, roomId);
            int rowsAffected = psmt.executeUpdate();
            if (rowsAffected > 0) {
                LOGGER.info("Room with ID {} has been deleted.", roomId);
            } else {
                LOGGER.error("No room found with ID {}.", roomId);
            }
        } catch (SQLException e) {
            LOGGER.error("Error updating room deleted status: {}", e.getMessage());
        }
    }
}