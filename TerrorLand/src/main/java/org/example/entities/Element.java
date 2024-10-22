package org.example.entities;

import org.example.enums.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Element {
    private final int element_id;
    private String name;
    private double price;
    private Type type;
    private static final Logger log = LoggerFactory.getLogger(Element.class);

    public Element (int element_id, String name, double price, Type type){
        this.element_id = element_id;
        this.name = name;
        this.price = price;
        this.type = type;
    }

    public int getElementId(){
        return this.element_id;
    }
    public String getName(){
        return this.name;
    }
    public double getPrice(){
        return this.price;
    }
    public Type getType(){
        return this.type;
    }
}
