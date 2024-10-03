package org.example.persistence;

import org.example.enums.Difficulty;
import org.example.enums.ElementType;

import java.lang.reflect.Type;

public class Room extends Element {
    private String diffLevel;

    public Room(String name, int quantity, double price, String theme,String diffLevel){
        super(name, quantity, price, theme);
        this.diffLevel = diffLevel;
    }

    public static Room createRoom(String name, int quantity, double price, String theme, String diffLevel){
        return new Room(name, quantity, price, theme, diffLevel);
    }

    @Override
    public String inputDataInfo() {
        return "INSERT INTO room (name, type, quantity, price, theme, difficulty) " +
                "VALUES ('" + super.getName() + "', 'ROOM', " + super.getQuantity() +
                ", " + super.getPrice() + ", '" + super.getTheme() + "', '" + this.diffLevel + "')";
    }
}
