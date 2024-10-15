package org.example.entities;

import org.example.dao.check.CheckForGeneratedKeyMySql;
import org.example.dao.generate.GenerateElementQueryMySql;
import org.example.dao.get.GetElementIdMySql;
import org.example.enums.Type;
import java.sql.*;
import org.example.util.MenuHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.example.database.MySQL.getConnectionFormatted;
import static org.example.mysql.MySqlHelper.getConnection;
import static org.example.util.IOHelper.readDouble;
import static org.example.util.IOHelper.readString;

public class Element {
    private final int element_id;
    private String name;
    private double price;
    private Type type;
    private final GenerateElementQueryMySql query = new GenerateElementQueryMySql();
    private final GetElementIdMySql getId = new GetElementIdMySql();
    private static final Logger log = LoggerFactory.getLogger(Element.class);
    private static final CheckForGeneratedKeyMySql set = new CheckForGeneratedKeyMySql();

    public Element (int element_id, String name, double price, Type type){
        this.element_id = element_id;
        this.name = name;
        this.price = price;
        this.type = type;
    }

    public int getElement_id(){
        return this.element_id;
    }
    public String getName(){
        return this.name;
    }
    public double getPrice(){
        return this.price;
    }
    public Type getType(){
        return this.type;
    }


    public static Element createAnElement(){
        int element_id = getCurrentElementId();
        String name = readString("Name of the element:\n>");
        double price = readDouble("Price of the element:\n");
        Type type = MenuHelper.readTypeSelection("Choose the element type");
        inputElementIntoTable(name, price, type);
        return new Element(element_id, name, price, type);
    }

    public static int getCurrentElementId() {
        int nextId = 0;
        String sql = "SELECT COALESCE(MAX(id_column), 0) AS next_id FROM element";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            nextId = rs.getInt("next_id");
        } catch (SQLException e) {
            log.error("Couldn't get the next element id number: {}", e.getMessage());
        }
        return nextId;
    }

    public static void inputElementIntoTable(String name, double price, Type type){
        String elementSqlQuery = "INSERT INTO element (name, price, type) VALUES (?, ?, ?)";
        log.info("Generated SQL query for element table: {}", elementSqlQuery);

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(elementSqlQuery)){
            pstmt.setString(1, name);
            pstmt.setDouble(2, price);
            pstmt.setString(3, type.toString());
            pstmt.executeUpdate();
        } catch (SQLException e){
            log.error("Error inputting values into the element table: {}", e.getMessage());
        }
    }

    public int generateElement(Type type) {
        int element_id = 0;
        try {
            Connection conn = getConnectionFormatted();
            String elementSQLQuery = generateElementQuery(type);
            //log.info("Generated SQL query for element table: {}", elementSQLQuery);
            PreparedStatement pstmt = conn.prepareStatement(elementSQLQuery, Statement.RETURN_GENERATED_KEYS);
            pstmt.executeUpdate();
            ResultSet setId = set.checkForGeneratedKey(pstmt.getGeneratedKeys());
            element_id = getId.getElementId(setId);
        } catch (SQLException e){
            log.error("Error creating an element: {}", e.getMessage());
        }
        return element_id;
    }

    public String generateElementQuery(Type type){
        String nameElem = readString("Name of the element:\n>");
        String theme = readString("Theme of the element:\n>");
        double price = readDouble("Price of the element:\n");
        return "INSERT INTO element (name, type, theme, price) VALUES " +
                "('" + nameElem + "', '" + type + "', '" + theme + "', " + price + ")";
    }
}
