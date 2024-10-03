package org.example.persistence;

import org.example.enums.ElementType;

public abstract class Element {
    private String name;
    private int quantity;
    private double price;
    private String theme;

    public Element(String name, int quantity, double price, String theme){
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.theme = theme;
    }

    public String getName(){
        return this.name;
    }
    public int getQuantity(){
        return this.quantity;
    }
    public double getPrice(){
        return this.price;
    }
    public String getTheme(){
        return this.theme;
    }

    public abstract String inputDataInfo();
}
