package org.example.database;

import org.example.enums.Difficulty;
import org.example.enums.Type;
import org.example.util.Menu;
import static org.example.enums.Difficulty.checkDifficulty;
import static org.example.persistence.Room.createRoom;

public class Element {
    private String name;
    private Type type;
    private int quantity;
    private float price;

    public Element(String name, Type type, int quantity, float price){
        this.name = name;
        this.type = type;
        this.quantity = quantity;
        this.price = price;
    }

}
