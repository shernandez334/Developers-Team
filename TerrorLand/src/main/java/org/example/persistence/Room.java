package org.example.persistence;

import org.example.database.Database;
import org.example.database.Element;
import org.example.enums.Difficulty;
import org.example.enums.Type;

import java.util.ArrayList;
import java.util.List;

public class Room extends Element {
    private Difficulty diffLevel;

    public Room(String name, Type type, int quantity, float price, Difficulty diffLevel){
        super(name, type, quantity, price);
        this.diffLevel = diffLevel;
    }

    public static Room createRoom(String name, Type type, int quantity, float price, Difficulty diffLevel){
        return new Room(name, type, quantity, price, diffLevel);
    }

    @Override
    public String dataInfo() {
        return "INSERT INTO room (difficulty) VALUES ('" + this.diffLevel + "')";
    }
}
