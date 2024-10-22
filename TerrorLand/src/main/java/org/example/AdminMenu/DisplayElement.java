package org.example.AdminMenu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static org.example.mysql.MySqlHelper.getConnection;


public class DisplayElement {
    private static final Logger LOGGER = LoggerFactory.getLogger(DisplayElement.class);
    private static final String displayAllRoomsQuery =
            "SELECT r.room_id, r.name, r.difficulty, e.element_id, e.name AS name_element, e.type, rhe.quantity,\n" +
                    "       d.material, c.theme\n" +
                    "FROM room r\n" +
                    "JOIN room_has_element rhe ON r.room_id = rhe.room_id\n" +
                    "JOIN element e ON e.element_id = rhe.element_id\n" +
                    "LEFT JOIN decor_item d ON e.element_id = d.element_id AND e.type = 'DECORATION'\n" +
                    "LEFT JOIN clue c ON e.element_id = c.element_id AND e.type = 'CLUE'\n" +
                    "WHERE r.deleted = 0;";

    public void displayAllRooms() {
        try (Connection conn = getConnection("escape_room");
             PreparedStatement psmt = conn.prepareStatement(displayAllRoomsQuery)){
            ResultSet rs = psmt.executeQuery();
            while (rs.next()) {
                String elementType = rs.getString("type");
                System.out.println("Room ID = " + rs.getInt("room_id") +
                                ", Room Name = " + rs.getString("name") +
                                ", Difficulty = " + rs.getString("difficulty") +
                                ", Element ID = " + rs.getInt("element_id") +
                                ", Element Name = " + rs.getString("name_element") +
                                ", Element Type = " + elementType +
                                ", Quantity = " + rs.getInt("quantity"));
                if ("DECORATION".equals(elementType)) {
                    System.out.println("Decoration Material: " + rs.getString("material"));
                }
                if ("CLUE".equals(elementType)) {
                    System.out.println("Clue Theme: " + rs.getString("theme"));
                }
                Thread.sleep(3000);
            }
        } catch (SQLException | InterruptedException e) {
            LOGGER.error("The display couldn't be shown: {}",e.getMessage());
        }
    }
}

