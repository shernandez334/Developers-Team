package org.example.dao.generate;


import static org.example.util.IOHelper.*;

public class GenerateElementQueryMySql implements GenerateElementQuery {

    @Override
    public String generateElementQuery(int elementType){
        String type = "";
        String nameElem = readString("Name of the element:\n>");
        String theme = readString("Theme of the element\n>");
        double price = readDouble("Price of the element\n");
        switch (elementType){
            case 1 -> type = "ROOM";
            case 2 -> type = "DECOR_ITEM";
            case 3 -> type = "CLUE";
        }
        return "INSERT INTO element (name, type, theme, price) VALUES " +
                "('" + nameElem + "', '" + type + "', '" + theme + "', " + price + ")";
    }
}
