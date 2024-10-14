package org.example.dao.element;

import org.example.dao.generate.GenerateElementIdMySql;
import org.example.dao.store.StoreElementMySql;
import org.example.enums.Difficulty;
import org.example.util.MenuHelper;

import java.sql.SQLException;

public class RoomMySql extends StoreElementMySql implements Room {
    private final GenerateElementIdMySql element = new GenerateElementIdMySql();

    @Override
    public String createElementRoom() throws SQLException{
        int element_id = element.generateElementId(1);
        storeElementInStorage(element_id);
        Difficulty difficulty = MenuHelper.readDifficultySelection("Choose a level of difficulty:");
        return "INSERT INTO room (element_id, difficulty) " +
                    "VALUES (" + element_id + ", " + difficulty + "');";
    }
}
