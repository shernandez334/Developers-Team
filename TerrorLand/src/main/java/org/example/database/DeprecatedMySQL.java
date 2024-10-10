package org.example.database;

import org.example.enums.Properties;
import org.example.exceptions.*;
import org.example.enums.DefaultProperties;

import java.nio.file.Path;
import java.sql.*;


public class DeprecatedMySQL {

    private static Connection connection;


    public static Connection getConnection(String dbName) throws SQLException, MySqlNotValidCredentialsException {

        if (DeprecatedMySQL.connection != null && DeprecatedMySQL.connection.isValid(0)) {
            return DeprecatedMySQL.connection;
        } else {
            String url = Properties.getProperty("db.url") + dbName;
            String user = Properties.getProperty("db.user");
            String password = Properties.getProperty("db.password");

            try {
                DeprecatedMySQL.connection = DriverManager.getConnection(url, user, password);
                System.out.printf("Connected to %s.%n", dbName.isEmpty() ? "DB" : dbName);
                return connection;

            } catch (SQLException e) {
                System.out.printf("Error connecting to '%s' as user '%s' and password '%s'.%n", url, user, password);
                if (e.getErrorCode() == 1045) {
                    throw new MySqlNotValidCredentialsException(String.format(
                            "Possible action: modify the credentials at '%s' if necessary.%n",
                            Path.of(DefaultProperties.PROPERTIES_FILE_PATH.getValue()).toAbsolutePath()));
                }
                throw e;
            }
        }


    }


    public static void inputDataInfo(String elementTypeQuery) {
        try (Connection connection = getConnection("escape_room");
             Statement stmt = connection.createStatement()) {
            System.out.println("Executing SQL: " + elementTypeQuery);
            stmt.executeUpdate(elementTypeQuery);
        } catch (SQLException | MySqlNotValidCredentialsException err) {
            err.printStackTrace();
        }
    }

    public static void displayStock(String showEnabledElemsQuery){
        try (Connection conn = getConnection("escape_room");
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

        } catch (SQLException | MySqlNotValidCredentialsException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void disableElement(int elementId){
        String query = "UPDATE stock_manager SET enabled = 0 WHERE element_id = ?";

        try (Connection conn = getConnection("escape_room"); // Replace with your database name
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, elementId); // Set the element ID in the query
            int affectedRows = pstmt.executeUpdate(); // Execute the update

            if (affectedRows > 0) {
                System.out.println("Element with ID " + elementId + " has been disabled.");
            } else {
                System.out.println("No element found with ID " + elementId + ".");
            }
        } catch (SQLException | MySqlNotValidCredentialsException e) {
            System.out.println("Error disabling element: " + e.getMessage());
        }

    }


    public static void executeQuery(String query){
        try (Connection connection = getConnection(DefaultProperties.DB_NAME.getValue());
             Statement statement = connection.createStatement()) {
            statement.execute(query);
        } catch (SQLException | MySqlNotValidCredentialsException e) {
            throw new RuntimeException(e);
        }
    }


}
