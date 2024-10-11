package org.example.database;

import org.example.enums.DefaultProperties;

import java.math.BigDecimal;
import java.sql.*;

import org.example.mysql.MySqlHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MySQL {

    private static final Logger log = LoggerFactory.getLogger(MySQL.class);

    public static Connection getConnectionFormatted() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/your_database_name"; // Specify your database name
        String user = "root";
        String password = "password"; // Consider externalizing this

        Connection conn = DriverManager.getConnection(url, user, password);

        if (!conn.isValid(2)) { // Check if the connection is valid with a timeout of 2 seconds
            throw new SQLException("Failed to establish a valid connection.");
        }

        return conn;
    }

    public static void inputDataInfo(String elementTypeQuery) {
        try (Connection connection = MySqlHelper.getConnection("escape_room");
             Statement stmt = connection.createStatement()) {
            log.info("Executing SQL: {}", elementTypeQuery);
            stmt.executeUpdate(elementTypeQuery);
        } catch (SQLException err) {
            log.error("{}, {}", err.getMessage(), elementTypeQuery);
        }
    }

    public static void displayStock(String showEnabledElemsQuery){
        try (Connection conn = MySqlHelper.getConnection("escape_room");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(showEnabledElemsQuery)) {
            System.out.println("Enabled Elements Information:");
            while (rs.next()) {
                int elementId = rs.getInt("element_id");
                String type = rs.getString("type");
                String name = rs.getString("name");
                String theme = rs.getString("theme");
                System.out.println("ID: " + elementId + " | Type: " + type + " | Name: " + name + " | theme: " + theme);
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void disableElement(int elementId){
        String query = "UPDATE stock_manager SET enabled = 0 WHERE element_id = ?";

        try (Connection conn = MySqlHelper.getConnection("escape_room"); // Replace with your database name
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, elementId); // Set the element ID in the query
            int affectedRows = pstmt.executeUpdate(); // Execute the update

            if (affectedRows > 0) {
                System.out.println("Element with ID " + elementId + " has been disabled.");
            } else {
                System.out.println("No element found with ID " + elementId + ".");
            }
        } catch (SQLException e) {
            System.out.println("Error disabling element: " + e.getMessage());
        }
    }

    @Deprecated
    public static BigDecimal getTotalIncomeOldVersion(){
        try (Connection connection = MySqlHelper.getConnection(DefaultProperties.DB_NAME.getValue());
             Statement statement = connection.createStatement()) {
            String str = "SELECT SUM(price) FROM ticket;";
            ResultSet result = statement.executeQuery(str);
            if (result.next()){
                return result.getBigDecimal(1);
            }else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
