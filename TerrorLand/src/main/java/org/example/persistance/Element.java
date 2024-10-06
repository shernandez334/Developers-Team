package org.example.persistance;


public abstract class Element {
    private String name;
    private int quantity;
    private double price;
    private String theme;
    private final int element_id;

    public Element(String name, int element_id, int quantity, double price, String theme){
        this.name = name;
        this.element_id = element_id;
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

    public int getElement_id(){
        return this.element_id;
    }

    public abstract String dataInfo();
}
