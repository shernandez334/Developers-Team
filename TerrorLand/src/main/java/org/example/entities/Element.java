package org.example.entities;

import org.example.enums.Type;

public class Element {
    private String name;
    private float price;
    private final Type type;

    public Element (String name, float price, Type type){
        this.name = name;
        this.price = price;
        this.type = type;
    }

    public String getName(){
        return this.name;
    }
    public float getPrice(){
        return this.price;
    }
    public Type getType(){
        return this.type;
    }
}
