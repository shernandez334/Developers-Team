package org.example.entities;

import org.example.enums.Difficulty;
import org.example.enums.Type;

public class Room {
    private final int element_id;
    private final Difficulty difficulty;

    public Room(int element_id , Difficulty difficulty){
       this.element_id = element_id;
        this.difficulty = difficulty;
    }

    public Difficulty getDifficulty(){
        return this.difficulty;
    }
}
