package org.example.persistence;

import org.example.enums.Type;

public class Decoration extends Element{
    public String material;

    public Decoration(String name, Type type, int quantity, double price, String theme, String material){
        super(name, type, quantity, price, theme);
        this.material = material;
    }

    public static Decoration createDecoration(String name, Type type, int quantity,
                                              double price, String theme, String material){

        return new Decoration(name, type, quantity, price, theme, material);
    }

    @Override
    public String inputDataInfo() {
        return "INSERT INTO room (name, type, quantity, price, theme, difficulty) " +
                "VALUES ('" + super.getName() + "', '" + super.getType() + "', " + super.getQuantity() +
                ", " + super.getPrice() + ", '" + super.getTheme() + "', '" + this.material + "')";
    }
}
