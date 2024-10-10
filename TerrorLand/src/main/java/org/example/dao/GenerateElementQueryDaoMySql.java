package org.example.dao;

import static org.example.util.IO.readInt;
import static org.example.util.IO.readString;

public class GenerateElementQueryDaoMySql implements GenerateElementQueryDao{

    @Override
    public String generateElementQuery(int elementType){
        int quantity = 0;
        String type = "";
        String nameElem = readString("Name of the element:\n>");
        String theme = readString("Theme of the element\n>");
        switch (elementType){
            case 1 -> {
                quantity = 1;
                type = "ROOM";
            }
            case 2 -> {
                quantity = readInt("Quantity:");
                type = "DECOR_ITEM";
            }
            case 3 -> {
                quantity = readInt("Quantity:");
                type = "CLUE";
            }
        }
        return "INSERT INTO element (name, type, quantity, theme) VALUES " +
                "('" + nameElem + "', '" + type + "', " + quantity + ", '" + theme + "')";
    }
}
