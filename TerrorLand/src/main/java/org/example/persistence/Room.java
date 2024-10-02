package org.example.persistence;

import org.example.enums.Difficulty;
import org.example.enums.Type;

public class Room extends Element {
    private Difficulty diffLevel;

    public Room(String name, Type type, int quantity, double price, String theme, Difficulty diffLevel){
        super(name, type, quantity, price, theme);
        this.diffLevel = diffLevel;
    }

    public static Room createRoom(String name, Type type, int quantity, double price, String theme, Difficulty diffLevel){
        return new Room(name, type, quantity, price, theme, diffLevel);
    }

    @Override
    public String inputDataInfo() {
        return "INSERT INTO room (name, type, quantity, price, theme, difficulty) " +
                "VALUES ('" + super.getName() + "', '" + super.getType() + "', " + super.getQuantity() +
                ", " + super.getPrice() + ", '" + super.getTheme() + "', '" + this.diffLevel + "')";
    }
}
