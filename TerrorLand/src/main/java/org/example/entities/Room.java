package org.example.entities;

import org.example.enums.Difficulty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Room{
    private final int room_id;
    private String name;
    private Difficulty difficulty;
    private int deleted;
    private static final Logger log = LoggerFactory.getLogger(Room.class);

    public Room(int room_id, String name, Difficulty difficulty, int deleted){
        this.name = name;
        this.room_id = room_id;
        this.difficulty = difficulty;
        this.deleted = deleted;
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
    public int getDeleted(){
        return this.deleted;
    }

    public void reset(){
        this.name = null;
        this.difficulty = null;
    }
}