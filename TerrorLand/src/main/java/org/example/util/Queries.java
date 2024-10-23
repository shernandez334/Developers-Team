package org.example.util;

public class Queries {

    public static String buildDisplayRoomQuery(){
        return """
                SELECT r.room_id, r.name, r.difficulty, e.element_id, e.name AS name_element, 
                       e.type, rhe.quantity, d.material, c.theme 
                FROM room r 
                JOIN room_has_element rhe ON r.room_id = rhe.room_id  
                JOIN element e ON e.element_id = rhe.element_id 
                LEFT JOIN decor_item d ON e.element_id = d.element_id AND e.type = 'DECORATION' 
                LEFT JOIN clue c ON e.element_id = c.element_id AND e.type = 'CLUE'
                """;
    }
}
