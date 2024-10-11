package org.example.dao.element;

import org.example.dao.generate.GenerateElementIdMySql;
import org.example.dao.store.StoreElementDaoMySql;
import org.example.enums.Difficulty;
import org.example.exceptions.ElementIdException;
import org.example.util.Menu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClueMySql extends StoreElementDaoMySql implements Clue {
    private final GenerateElementIdMySql element = new GenerateElementIdMySql();
    private static final Logger log = LoggerFactory.getLogger(ClueMySql.class);

    @Override
    public String createElementClue() throws ElementIdException{
        String query;
        Difficulty difficulty;
        try {
            int element_id = element.generateElementId(1);
            difficulty = Menu.readDifficultySelection("Choose a level of difficulty:");
            query = "INSERT INTO room (element_id, price, difficulty) " +
                    "VALUES (" + element_id + ", " + 23 + ", '" + difficulty + "');";
            storeElementInStorage(element_id);
        } catch (ElementIdException e){
            log.error("Error creating room: {}", e.getMessage());
            throw e;
        }
        return query;
    }
}
