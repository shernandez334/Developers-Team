package org.example.persistance;

public class Decoration extends Element{
    private String material;

    public Decoration(String name, int element_id, int quantity, double price, String theme, String material){
        super(name, element_id, quantity, price, theme);
        this.material = material;
    }

    public static Decoration createDecoration(String name,  int element_id, int quantity,
                                              double price, String theme, String material){
        return new Decoration(name, element_id, quantity, price, theme, material);
    }

    @Override
    public String dataInfo() {
        return "INSERT INTO decor_item (name, element_id, type, quantity, price, theme, material) " +
                "VALUES ('" + super.getName() + "', " + super.getElement_id() + ", 'DECOR_ITEM', " +
                super.getQuantity() + ", " + super.getPrice() + ", '" + super.getTheme() + "', '" + this.material +  "')";
    }
}