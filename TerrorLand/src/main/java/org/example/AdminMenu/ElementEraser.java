package org.example.AdminMenu;

import org.example.util.IOHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.example.mysql.MySqlHelper.getConnection;

public class ElementEraser {
    private static final ElementDisplay DISPLAY = new ElementDisplay();
    private static final Logger LOGGER = LoggerFactory.getLogger(ElementEraser.class);

    public void eraseRoom(){
        //DISPLAY.displayUndeletedRooms();
        int roomDeleted = IOHelper.readInt("Enter the room Id you want to delete:\n");
        updateRoomDeletedStatus(roomDeleted);
    }

    public void updateRoomDeletedStatus(int roomId) {
        String updateQuery = "UPDATE room SET deleted = ? WHERE room_id = ?";
        try (Connection conn = getConnection("escape_room");
             PreparedStatement psmt = conn.prepareStatement(updateQuery)) {
            psmt.setInt(1, 1);
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
