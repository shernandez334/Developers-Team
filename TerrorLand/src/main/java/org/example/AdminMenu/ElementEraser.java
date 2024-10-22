package org.example.AdminMenu;

import org.example.util.IOHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.example.mysql.MySqlHelper.getConnection;

public class ElementEraser {
    private static final DisplayElement DISPLAY = new DisplayElement();
    private static final Logger LOGGER = LoggerFactory.getLogger(ElementEraser.class);

    public void eraseRoom(){
        DISPLAY.displayAllRooms();
        int roomIdDeleted = IOHelper.readInt("Enter the room Id you want to delete:\n");
        updateRoomDeletedStatus(roomIdDeleted);
    }

    public void updateRoomDeletedStatus(int roomId) {
        String updateQuery = "UPDATE room SET deleted = ? WHERE room_id = ?";
        try (Connection conn = getConnection("escape_room");
             PreparedStatement psmt = conn.prepareStatement(updateQuery)) {
            psmt.setInt(1, 1);
            psmt.setInt(2, roomId);
            int rowsAffected = psmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Room with ID " + roomId + " has been undeleted.");
            } else {
                System.out.println("No room found with ID " + roomId + ".");
            }
        } catch (SQLException e) {
            LOGGER.error("Error updating room deleted status: {}", e.getMessage());
        }
    }
}
