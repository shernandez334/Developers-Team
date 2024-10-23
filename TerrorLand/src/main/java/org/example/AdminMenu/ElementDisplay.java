package org.example.AdminMenu;
import org.example.util.Queries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static org.example.mysql.MySqlHelper.getConnection;


public class ElementDisplay {
    private static final Logger LOGGER = LoggerFactory.getLogger(ElementDisplay.class);
    private static StringBuilder displayOutput = new StringBuilder();
    private static String displayRoomQuery = Queries.buildDisplayRoomQuery();

    public void displayRooms(int displayDeletedMenu) {
        String finalQuery = displayRoomQuery + (displayDeletedMenu == 0 ? "WHERE r.deleted = 0;" : "WHERE r.deleted = 1;");
        try (Connection conn = getConnection("escape_room");
             PreparedStatement psmt = conn.prepareStatement(finalQuery)){
            ResultSet rs = psmt.executeQuery();
            while (rs.next()) {
                displayOutput.setLength(0);
                String roomDetails = buildRoomOutput(rs);
                System.out.println(roomDetails);
            }
        } catch (SQLException e) {
            LOGGER.error("The display couldn't be shown: {}",e.getMessage());
        }
    }

    private String buildRoomOutput(ResultSet rs) throws SQLException {
        StringBuilder output = new StringBuilder();
        String elementType = rs.getString("type");

        output.append("Room ID = ").append(rs.getInt("room_id"))
                .append(", Room Name = ").append(rs.getString("name"))
                .append(", Difficulty = ").append(rs.getString("difficulty"))
                .append(", Element ID = ").append(rs.getInt("element_id"))
                .append(", Element Name = ").append(rs.getString("name_element"))
                .append(", Element Type = ").append(elementType)
                .append(", Quantity = ").append(rs.getInt("quantity"));
        if ("DECORATION".equals(elementType)) {
            output.append(", Decoration Material: ").append(rs.getString("material"));
        } else if ("CLUE".equals(elementType)) {
            output.append(", Clue: ").append(rs.getString("theme"));
        }
        return output.toString();
    }
}

