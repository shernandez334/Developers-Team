package org.example.entities;

import org.example.enums.Difficulty;
import org.example.enums.Type;

public class Room extends Element{
    private final Difficulty difficulty;
    

    public Room(String name, float price, Type type, Difficulty difficulty){
        super(name, price, type);
        this.difficulty = difficulty;
    }

}
