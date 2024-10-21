package org.example.entities;

import org.example.enums.Difficulty;
import org.example.enums.Type;
import org.example.util.IOHelper;
import org.example.util.MenuHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.example.mysql.MySqlHelper.getConnection;

public class Room{
    private final int room_id;
    private final String name;
    private final Difficulty difficulty;
    private static final Logger log = LoggerFactory.getLogger(Room.class);

    public Room(int room_id, String name, Difficulty difficulty){
        this.name = name;
        this.room_id = room_id;
        this.difficulty = difficulty;
    }

    public int getRoomId(){
        return this.room_id;
    }
    public String getNameRoom(){
        return this.name;
    }
    public Difficulty getDifficulty(){
        return this.difficulty;
    }

}