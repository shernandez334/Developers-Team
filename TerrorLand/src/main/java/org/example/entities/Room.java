package org.example.entities;

import org.example.enums.Difficulty;
import org.example.enums.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.example.mysql.MySqlHelper.getConnection;

public class Room{
    private final int element_id;
    private final Difficulty difficulty;
    private static final Logger log = LoggerFactory.getLogger(Room.class);

    public Room(int element_id , Difficulty difficulty){
        this.element_id = element_id;
       this.difficulty = difficulty;
    }

    public int getElement_id(){
        return this.element_id;
    }
    public Difficulty getDifficulty(){
        return this.difficulty;
    }

    public static void inputRoomIntoTable(Room room){
        String roomSqlQuery = "INSERT INTO room (element_id, difficulty) VALUES (?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(roomSqlQuery)){
            pstmt.setString(1, this.element_id);
            pstmt.setDouble(2, this.difficulty);
            pstmt.executeUpdate();
        } catch (SQLException e){
            log.error("Error inputting values into the element table: {}", e.getMessage());
        }
    }
}