package org.example.persistence;

import org.example.enums.Type;

public class Clue extends Element{

    public Clue(String name, Type type, int quantity, double price, String theme){
        super(name, type, quantity, price, theme);
    }

    public static Element createClue(String name, Type type, int quantity, double price, String theme){
        return new Clue(name, type, quantity, price, theme);
    }

    @Override
    public String inputDataInfo(){
        return "INSERT INTO room (name, type, quantity, price, theme, difficulty) " +
                "VALUES ('" + super.getName() + "', '" + super.getType() + "', " + super.getQuantity() +
                ", " + super.getPrice() + ", '" + super.getTheme() + "')";
    }
}
