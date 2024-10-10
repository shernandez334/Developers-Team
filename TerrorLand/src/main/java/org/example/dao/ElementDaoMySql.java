package org.example.dao;

import org.example.exceptions.ElementIdException;
import org.example.util.Menu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.example.database.MySQL.*;
import static org.example.util.IO.*;
import static org.example.util.IO.readInt;

public class ElementDaoMySql implements ElementDao{
    private final RoomDaoMySql roomDaoMySql = new RoomDaoMySql();
    private final DecorationDao decorationDao = new DecorationDaoMySql();
    private final ClueDaoMySql clueDaoMySql = new ClueDaoMySql();
    private final GenerateElementIdDaoMySql elementId = new GenerateElementIdDaoMySql();
    private static final Logger log = LoggerFactory.getLogger(ElementDaoMySql.class);

    @Override
    public void createAnElement(){
        String query = "";
        int op = Menu.readSelection("What element would you like to create?", ">",
                "1. Room", "2. Decoration", "3. Clue");
        try {
            switch (op){
                case 1 -> query = roomDaoMySql.createElementRoom();
                case 2 -> query = decorationDao.createElementDecoration();
                case 3 -> query = clueDaoMySql.createElementClue();
            }
        } catch (ElementIdException e){
            log.error("Error occurred while creating an element: {}", e.getMessage());
        }
    }

    @Override
    public void deleteAnElement(){
        String showEnabledElemsQuery = """
                SELECT e.element_id, e.name, e.type, e.theme,
                           r.price, r.difficulty
                    FROM element e
                    LEFT JOIN room r ON e.element_id = r.element_id
                    JOIN stock_manager sm ON e.element_id = sm.element_id
                    WHERE sm.enabled = 1;
                """;
        displayStock(showEnabledElemsQuery);
        int elementIdToDelete = readInt("Number of the ID Element you want to eliminate:");
        disableElement(elementIdToDelete);
    }
}
