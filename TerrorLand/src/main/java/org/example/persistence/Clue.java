package org.example.persistence;

import org.example.enums.ElementType;

public class Clue extends Element{
    private ElementType type = ElementType.CLUE;

    public Clue(String name, int quantity, double price, String theme){
        super(name, quantity, price, theme);
    }

    public static Element createClue(String name, int quantity, double price, String theme){
        return new Clue(name, quantity, price, theme);
    }

    @Override
    public String inputDataInfo(){
        return "INSERT INTO room (name, type, quantity, price, theme, difficulty) " +
                "VALUES ('" + super.getName() + "', '" + this.type + "', " + super.getQuantity() +
                ", " + super.getPrice() + ", '" + super.getTheme() + "')";
    }
}
