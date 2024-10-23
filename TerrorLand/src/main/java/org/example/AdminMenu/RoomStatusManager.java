package org.example.AdminMenu;

import org.example.util.IOHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.example.mysql.MySqlHelper.getConnection;

public class RoomStatusManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(RoomStatusManager.class);
    private static final ElementDisplay DISPLAY = new ElementDisplay();

    public void modifyRoomStatus(int diplayRoomStatus, int newRoomStatus){
        DISPLAY.displayRooms(diplayRoomStatus);
        int roomId = IOHelper.readInt("Enter the Room Id you want to delete: ");
        updateRoomStatus(roomId, newRoomStatus);
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
