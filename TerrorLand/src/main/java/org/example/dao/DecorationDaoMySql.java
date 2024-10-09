package org.example.dao;

import static org.example.util.IO.readDouble;
import static org.example.util.IO.readString;

public class DecorationDaoMySql extends StoreElementDaoMySql implements DecorationDao{
    private final ElementDao elementDao = new ElementDaoMySql();

    @Override
    public String createElementDecoration(){
        String query;
        double price;
        String material;
        int element_id = elementDao.inputElementTableInfo(2);
        if (element_id == -1){
            System.out.println("Failed to create element.");
            query = null;
        } else {
            price = readDouble("Price of the element:\n>");
            material = readString("Material:\n");
            query = "INSERT INTO decor_item (element_id, price, material) " +
                    "VALUES (" + element_id + ", " + price + ", '" + material + "');";
            storeElementInStorage(element_id);
        }
        return query;
    }
}
