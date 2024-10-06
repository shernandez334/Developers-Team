package org.example.persistance;

import org.example.exceptions.MySqlCredentialsException;
import org.example.util.Menu;

import java.sql.*;

import static org.example.database.MySQL.getConnection;
import static org.example.database.MySQL.inputDataInfo;
import static org.example.persistance.Difficulty.getDifficultyLevel;
import static org.example.util.IO.*;


public class ElementCreator {

    public static void createAnElement(){
        Element e = null;
        String query = "";
        int op = Menu.readSelection("What element would you like to create?", ">",
                "1. Room", "2. Decoration", "3. Clue");
        switch (op){
            case 1 -> query = createElementRoom();
            case 2 -> query = createElementDecoration();
            case 3 -> query = createElementClue();
        }
        if (query != null){
            inputDataInfo(query);
        } else {
            System.out.println("Failed to create the element. Please try again.");
        }
    }

    public static String createElementRoom(){
        String query = "";
        double price = 0d;
        String difficulty = "";
        int element_id = inputElementTableInfo(1);
        if (element_id == -1){
            System.out.println("Failed to create element.");
            query = null;
        } else {
            price = readFloat("Price of the element:\n>");
            difficulty = getDifficultyLevel("Choose a level of difficulty:");
            query = "INSERT INTO room (element_id, price, difficulty) " +
                    "VALUES (" + element_id + ", " + price + ", '" + difficulty + "');";
            storeElementInStorage(element_id);
        }
        return query;
    }

    public static String createElementDecoration(){
        String query = "";
        double price = 0d;
        String material = "";
        int element_id = inputElementTableInfo(2);
        if (element_id == -1){
            System.out.println("Failed to create element.");
            query = null;
        } else {
            price = readFloat("Price of the element:\n>");
            material = readString("Material:\n");
            query = "INSERT INTO decor_item (element_id, price, material) " +
                    "VALUES (" + element_id + ", " + price + ", '" + material + "');";
            storeElementInStorage(element_id);
        }
        return query;
    }

    public static String createElementClue(){
        String query = "";
        int element_id = inputElementTableInfo(3);
        if (element_id == -1){
            System.out.println("Failed to create element.");
            query = null;
        } else {
            query = "INSERT INTO clue (element_id) " +
                    "VALUES (" + element_id + ");";
            storeElementInStorage(element_id);
        }
        return query;
    }

    public static int inputElementTableInfo(int elementType){
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

    public static String elementTableQuery(int elementType){
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

    public static void storeElementInStorage(int element_id) {
        String query = "INSERT INTO stock_manager (element_id, enabled, available) " +
                "VALUES ('" + element_id + "', '" + 1 + "', '" + 1 + "');";
        inputDataInfo(query);
    }
}