package org.example.util;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OutputBuilder {

    public String buildRoomOutput(ResultSet rs) throws SQLException {
        StringBuilder output = new StringBuilder();
        output.append("Room ID = ").append(rs.getInt("room_id"))
                .append(", Room Name = ").append(rs.getString("name"))
                .append(", Difficulty = ").append(rs.getString("difficulty"));
        return output.toString();
    }

    public String buildElementOutput(ResultSet rs) throws SQLException {
        StringBuilder output = new StringBuilder();
        output.append("Element ID = ").append(rs.getInt("element_id"))
                .append(", Element Name = ").append(rs.getString("element_name")) // Update here
                .append(", Type = ").append(rs.getString("type"))
                .append(", Quantity = ").append(rs.getInt("quantity"));
        String type = rs.getString("type");
        if ("DECORATION".equals(type)) {
            output.append(", Material = ").append(rs.getString("material"));
        } else if ("CLUE".equals(type)) {
            output.append(", Theme = ").append(rs.getString("theme"));
        }
        return output.toString();
    }

    public String buildTotalPriceOutput(ResultSet rs) throws SQLException {
        return "Total Price of All Elements in All Rooms: $" + rs.getBigDecimal("total_price");
    }

}
