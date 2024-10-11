package org.example.dao.element;

import org.example.dao.generate.GenerateElementIdMySql;
import org.example.exceptions.ElementIdException;
import org.example.util.MenuHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.example.database.MySQL.*;
import static org.example.util.IOHelper.readInt;

public class ElementMySql implements Element {
    private final RoomMySql roomDaoMySql = new RoomMySql();
    private final Decoration decoration = new DecorationMySql();
    private final ClueMySql clueDaoMySql = new ClueMySql();
    private final GenerateElementIdMySql elementId = new GenerateElementIdMySql();
    private static final Logger log = LoggerFactory.getLogger(ElementMySql.class);

    @Override
    public void createAnElement(){
        int op = MenuHelper.readSelection("What element would you like to create?", ">",
                "1. Room", "2. Decoration", "3. Clue");
        try {
            switch (op){
                case 1 -> roomDaoMySql.createElementRoom();
                case 2 -> decoration.createElementDecoration();
                case 3 -> clueDaoMySql.createElementClue();
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
