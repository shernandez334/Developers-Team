package org.example.mysql;

public class Queries {

    public static String buildDisplayAllRoomsQuery(){
        return """
                SELECT r.room_id, r.name, r.difficulty
                FROM room r 
                """;
    }

    public static String buildDisplayElementQuery(){
        return """
                SELECT
                    e.element_id,
                    e.name AS element_name,
                    e.type,
                    rhe.quantity,
                    d.material,
                    c.theme
                FROM
                    room_has_element rhe
                JOIN
                    element e ON e.element_id = rhe.element_id
                JOIN
                    room r ON r.room_id = rhe.room_id AND r.deleted = ?
                LEFT JOIN
                    decor_item d ON e.element_id = d.element_id AND e.type = 'DECORATION'
                LEFT JOIN
                    clue c ON e.element_id = c.element_id AND e.type = 'CLUE'
                WHERE
                    rhe.room_id = ?;
            """;
    }

    public static String buildCalculateStockValue(){
        return """
                SELECT SUM(e.price * rhe.quantity) AS total_price
                FROM room r
                JOIN room_has_element rhe ON r.room_id = rhe.room_id
                JOIN element e ON e.element_id = rhe.element_id
                WHERE r.deleted = 0;
               """;
    }
}