package org.example.persistence;

import org.example.enums.ElementType;

public class Decoration extends Element{
    private final ElementType type = ElementType.DECOR_ITEM;
    private String material;

    public Decoration(String name, int quantity, double price, String theme, String material){
        super(name, quantity, price, theme);
        this.material = material;
    }

    public static Decoration createDecoration(String name,  int quantity,
                                              double price, String theme, String material){

        return new Decoration(name, quantity, price, theme, material);
    }

    @Override
    public String inputDataInfo() {
        return "INSERT INTO room (name, type, quantity, price, theme, difficulty) " +
                "VALUES ('" + super.getName() + "', '" +  this.type + "', " + super.getQuantity() +
                ", " + super.getPrice() + ", '" + super.getTheme() + "', '" + this.material + "')";
    }
}
