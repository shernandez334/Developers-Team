package org.example.persistance;


public class Clue extends Element{

    public Clue(String name, int element_id,  int quantity, double price, String theme){
        super(name, element_id, quantity, price, theme);
    }

    public static Element createClue(String name, int element_id, int quantity, double price, String theme){
        return new Clue(name, element_id, quantity, price, theme);

    }

    @Override
    public String dataInfo() {
        return "INSERT INTO clue (name, element_id, type, quantity, price, theme) " +
                "VALUES ('" + super.getName() + "', " + super.getElement_id() + ", 'CLUE', " +
                super.getQuantity() + ", " + super.getPrice() + ", '" + super.getTheme() +  "')";
    }
}
