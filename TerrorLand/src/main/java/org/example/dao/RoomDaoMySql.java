package org.example.dao;

import org.example.enums.Difficulty;
import org.example.util.Menu;
import static org.example.util.IO.readDouble;

public class RoomDaoMySql extends StoreElementDaoMySql implements RoomDao{
    private final ElementDao elementDao = new ElementDaoMySql();


    public String createElementRoom(){
        String query;
        double price;
        Difficulty difficulty;
        int element_id = elementDao.inputElementTableInfo(1);
        if (element_id == -1){
            System.out.println("Failed to create element.");
            query = null;
        } else {
            price = readDouble("Price of the element:\n>");
            difficulty = Menu.readDifficultySelection("Choose a level of difficulty:");
            query = "INSERT INTO room (element_id, price, difficulty) " +
                    "VALUES (" + element_id + ", " + price + ", '" + difficulty + "');";
            storeElementInStorage(element_id);
        }
        return query;
    }
}
