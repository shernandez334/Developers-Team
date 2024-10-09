package org.example.dao;

import org.example.enums.Difficulty;
import org.example.exceptions.MySqlCredentialsException;
import org.example.util.Menu;
import java.sql.*;

import static org.example.database.MySQL.*;
import static org.example.util.IO.*;
import static org.example.util.IO.readInt;

public class ElementDaoMySql implements ElementDao{
    private final RoomDaoMySql roomDaoMySql = new RoomDaoMySql();
    private final StoreElementDao storeElementDao = new StoreElementDaoMySql();

    @Override
    public void createAnElement(){
        String query = "";
        int op = Menu.readSelection("What element would you like to create?", ">",
                "1. Room", "2. Decoration", "3. Clue");
        switch (op){
            case 1 -> query = roomDaoMySql.createElementRoom();
            case 2 -> query = createElementDecoration();
            case 3 -> query = createElementClue();
        }
        if (query != null){
            inputDataInfo(query);
        } else {
            System.out.println("Failed to create the element. Please try again.");
        }
    }

    @Override
    public String createElementDecoration(){
        String query;
        double price;
        String material;
        int element_id = inputElementTableInfo(2);
        if (element_id == -1){
            System.out.println("Failed to create element.");
            query = null;
        } else {
            price = readDouble("Price of the element:\n>");
            material = readString("Material:\n");
            query = "INSERT INTO decor_item (element_id, price, material) " +
                    "VALUES (" + element_id + ", " + price + ", '" + material + "');";
            storeElementDao.storeElementInStorage(element_id);
        }
        return query;
    }

    @Override
    public String createElementClue(){
        String query;
        int element_id = inputElementTableInfo(3);
        if (element_id == -1){
            System.out.println("Failed to create element.");
            // Exception createElement
            query = null;
        } else {
            query = "INSERT INTO clue (element_id) " +
                    "VALUES (" + element_id + ");";
            storeElementDao.storeElementInStorage(element_id);
        }
        return query;
    }

    @Override
    public
    int inputElementTableInfo(int elementType){
        int element_id = -1;
        String insertElementSQL = elementTableQuery(elementType);
        try (Connection conn = getConnection("escape_room")) {
            PreparedStatement pstmt = conn.prepareStatement(insertElementSQL, Statement.RETURN_GENERATED_KEYS);
            pstmt.executeUpdate();
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    element_id = generatedKeys.getInt(1);
                }
            }
        } catch (SQLException | MySqlCredentialsException e){
            System.out.println(e.getMessage());
        }
        return element_id;
    }

    @Override
    public String elementTableQuery(int elementType){
        int quantity = 0;
        String type = "";
        String nameElem = readString("Name of the element:\n>");
        String theme = readString("Theme of the element\n>");
        switch (elementType){
            case 1 -> {
                quantity = 1;
                type = "ROOM";
            }
            case 2 -> {
                quantity = readInt("Quantity:");
                type = "DECOR_ITEM";
            }
            case 3 -> {
                quantity = readInt("Quantity:");
                type = "CLUE";
            }
        }
        return "INSERT INTO element (name, type, quantity, theme) VALUES " +
                "('" + nameElem + "', '" + type + "', " + quantity + ", '" + theme + "')";
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
