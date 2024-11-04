package org.example.dao;

import org.example.enums.Difficulty;
import org.example.enums.Material;
import org.example.enums.Theme;
import org.example.enums.Type;
import org.example.factory.DatabaseInputFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.example.mysql.MySqlHelper.getConnection;

public class DatabaseInputDaoSQL implements DatabaseInputFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseInputDaoSQL.class);

    public void inputElementIntoTable(String name, double price, Type type) {
        String elementSqlQuery = "INSERT INTO element (name, price, type, deleted) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection("escape_room");
             PreparedStatement pstmt = conn.prepareStatement(elementSqlQuery)) {
            pstmt.setString(1, name);
            pstmt.setDouble(2, price);
            pstmt.setString(3, String.valueOf(type));
            pstmt.setInt(4, 1);
            pstmt.executeUpdate();
            LOGGER.info("Generated SQL query for element table: {}", elementSqlQuery);
        } catch (SQLException e) {
            LOGGER.error("Error inputting values into the element table: {}", e.getMessage());
        }
    }

    @Override
    public void inputDecorationIntoTable(int element_id, Material material){
        String elementSqlQuery = "INSERT INTO decor_item (element_id, material) VALUES (?, ?)";
        LOGGER.info("Generated SQL query for the decoration table: {}", elementSqlQuery);

        try (Connection conn = getConnection("escape_room");
             PreparedStatement pstmt = conn.prepareStatement(elementSqlQuery)){
            pstmt.setInt(1, element_id);
            pstmt.setString(2, String.valueOf(material));
            pstmt.executeUpdate();
        } catch (SQLException e){
            LOGGER.error("Error inputting values into the decoration table: {}", e.getMessage());
        }
    }

    @Override
    public void inputClueIntoTable(int element_id, Theme theme){
        String elementSqlQuery = "INSERT INTO clue (element_id, theme) VALUES (?, ?)";

        try (Connection conn = getConnection("escape_room");
             PreparedStatement pstmt = conn.prepareStatement(elementSqlQuery)){
            pstmt.setInt(1, element_id);
            pstmt.setString(2, String.valueOf(theme));
            pstmt.executeUpdate();
        } catch (SQLException e){
            LOGGER.error("Error inputting values into the clue table: {}", e.getMessage());
        }
    }

    @Override
    public void inputRoomIntoTable(String name, Difficulty difficulty, int deleted){
        String roomSqlQuery = "INSERT INTO room (name, difficulty, deleted) VALUES (?, ?, ?)";
        try (Connection conn = getConnection("escape_room");
             PreparedStatement pstmt = conn.prepareStatement(roomSqlQuery)){
            pstmt.setString(1, name);
            pstmt.setString(2, difficulty.name());
            pstmt.setInt(3, deleted);
            pstmt.executeUpdate();
        } catch (SQLException e){
            LOGGER.error("Error inputting values into the room table: {}", e.getMessage());
        }
    }

    @Override
    public void inputRoomHasElementIntoTable(int room_id, int element_id, int quantity){
        String roomSqlQuery = "INSERT INTO room_has_element (room_id, element_id, quantity) VALUES (?, ?, ?)";
        try (Connection conn = getConnection("escape_room");
             PreparedStatement pstmt = conn.prepareStatement(roomSqlQuery)){
            pstmt.setInt(1, room_id);
            pstmt.setInt(2, element_id);
            pstmt.setInt(3, quantity);
            pstmt.executeUpdate();
        } catch (SQLException e){
            LOGGER.error("Error inputting values into the room_has_elements table: {}", e.getMessage());
        }
    }

    public boolean doesRoomHaveElements(int roomId) {
        String query = "SELECT COUNT(*) FROM room_has_element WHERE room_id = ?";
        try (Connection conn = getConnection("escape_room");
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, roomId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            LOGGER.info("It could´t be checked if room_has_element {}", e.getMessage());
        }
        return false;
    }
}