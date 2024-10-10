package org.example.persistance;

import static org.example.database.MySQL.*;
import static org.example.util.IO.readInt;

public class ElementEraser {
    private static String showEnabledElemsQuery = """
            SELECT e.element_id, e.name, e.type, e.theme,
                       r.price, r.difficulty
                FROM element e
                LEFT JOIN room r ON e.element_id = r.element_id
                JOIN stock_manager sm ON e.element_id = sm.element_id
                WHERE sm.enabled = 1;
            """;

    public static void deleteAnElement(){
        displayStock(showEnabledElemsQuery);
        int elementIdToDelete = readInt("Number of the ID Element you want to eliminate:");
        disableElement(elementIdToDelete);
    }
}
