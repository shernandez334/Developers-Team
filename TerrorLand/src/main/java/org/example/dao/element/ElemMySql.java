package org.example.dao.element;

import org.example.dao.generate.GenerateElementIdMySql;
import org.example.util.MenuHelper;

import java.sql.SQLException;

import static org.example.database.MySQL.*;
import static org.example.util.IOHelper.readInt;

public class ElemMySql implements Elem {
    private final RoomMySql roomDaoMySql = new RoomMySql();
    private final Decoration decoration = new DecorationMySql();
    private final ClueMySql clueDaoMySql = new ClueMySql();
    private final GenerateElementIdMySql elementId = new GenerateElementIdMySql();

    @Override
    public void createAnElement() throws SQLException {
        int op = MenuHelper.readSelection("What element would you like to create?", ">",
                "1. Room", "2. Decoration", "3. Clue");
        switch (op){
                case 1 -> roomDaoMySql.createElementRoom();
                case 2 -> decoration.createElementDecoration();
                case 3 -> clueDaoMySql.createElementClue();
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
