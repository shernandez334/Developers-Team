package org.example.database;

import org.example.enums.Properties;
import org.example.exceptions.ExistingEmailException;
import org.example.exceptions.MySqlCredentialsException;
import org.example.exceptions.RunSqlFileException;
import org.example.logic.Admin;
import org.example.logic.Player;
import org.example.logic.User;
import org.example.persistance.Element;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.util.Arrays;



public class MySQL implements Database {

    private static Connection connection;

    /*
    public static void loadDriver(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException |
                 ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
     */

    public static Connection getConnection(String dbName) throws SQLException, MySqlCredentialsException {

        if (MySQL.connection != null && MySQL.connection.isValid(0)) {
            return MySQL.connection;
        } else {
            String url = Properties.getProperty("db.url") + dbName;
            String user = Properties.getProperty("db.user");
            String password = Properties.getProperty("db.password");

            try {
                MySQL.connection = DriverManager.getConnection(url, user, password);
                System.out.printf("Connected to %s.%n", dbName.isEmpty() ? "DB" : dbName);
                return connection;

            } catch (SQLException e) {
                System.out.printf("Error connecting to '%s' as user '%s' and password '%s'%n", url, user, password);
                if (e.getErrorCode() == 1045) {
                    throw new MySqlCredentialsException(String.format(
                            "Error connecting to MySQL server. Modify the credentials at '%s'%n",
                            Path.of(Properties.PROPERTIES_FILE_PATH.getValue()).toAbsolutePath()),
                            e);
                }
                throw e;
            }
        }


    }

    public static Connection getConnection() throws SQLException, MySqlCredentialsException {
        return getConnection("");
    }

    public void createIfMissing() throws MySqlCredentialsException {

        try {
            MySQL.connection = getConnection(Properties.DB_NAME.getValue());
        } catch (SQLException e) {
            if (e.getErrorCode() == 1049) {
                System.out.println("Unknown database -> Creating Database...");
                executeSqlFile(Properties.SQL_SCHEMA_CREATION_FILE.getValue());
            } else {
                throw new RuntimeException(e);
            }
        }
    }

    private static void executeSqlFile(String file) throws MySqlCredentialsException {
        Path path = Path.of(file.replace("\\", "/"));
        System.out.printf("Attempting to read SQL file from: %s%n", path);
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             BufferedReader input = Files.newBufferedReader(path.toRealPath())
        ) {
            String createDatabaseSql = String.format("CREATE DATABASE IF NOT EXISTS %s;", Properties.DB_NAME.getValue());
            statement.execute(createDatabaseSql);
            statement.execute("USE " + Properties.DB_NAME.getValue());
            StringBuilder sql = new StringBuilder();
            input.lines().filter(s -> !s.isEmpty() && !s.matches("^--.*")).forEach(sql::append);

            Arrays.stream(sql.toString().split(";")).forEach(s -> {
                try {
                    System.out.println(s);
                    statement.execute(s);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
            System.out.println("DB created successfully.");

        } catch (SQLException e) {
            throw new RuntimeException("Unable to execute the .sql script: MySQL error.", e);
        } catch (IOException e) {
            throw new RunSqlFileException("Unable to execute the .sql script: error reading the '.sql' file.", e);
        }
    }

    public static void inputDataInfo(String elementTypeQuery) {
        try (Connection connection = getConnection("escape_room");
             Statement stmt = connection.createStatement()) {
            String sql = elementTypeQuery;
            System.out.println("Executing SQL: " + sql);
            stmt.executeUpdate(sql);
        } catch (SQLException | MySqlCredentialsException err) {
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

        } catch (SQLException | MySqlCredentialsException e) {
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
        } catch (SQLException | MySqlCredentialsException e) {
            System.out.println("Error disabling element: " + e.getMessage());
        }

    }

    public boolean addUser(User user) throws ExistingEmailException {

        try (Connection connection = getConnection(Properties.DB_NAME.getValue());
             Statement statement = connection.createStatement()) {
            String str = String.format("INSERT INTO user (name, email, password, role) VALUES('%s', '%s', '%s', '%s');",
                    user.getName(), user.getEmail(), user.getPassword(), user instanceof Player ? "player" : "admin");
            statement.execute(str);
            return statement.getUpdateCount() == 1;
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new ExistingEmailException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (MySqlCredentialsException e) {
            throw new RuntimeException(e);
        }
    }

    public static User getUser(String email, String password) {
        User user;
        try (Connection connection = getConnection(Properties.DB_NAME.getValue());
             Statement statement = connection.createStatement()) {
            String str = String.format("SELECT * FROM user WHERE email = '%s' AND password = '%s';", email, password);
            ResultSet result = statement.executeQuery(str);
            if (!result.next()){
                user = null;
            } else if (result.getString("role").equalsIgnoreCase("player")){
                user = new Player(result.getString("name"), result.getString("email"), result.getInt("user_id"));
            }else {
                user = new Admin(result.getString("name"), result.getString("email"), result.getInt("user_id"));
            }
            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (MySqlCredentialsException e) {
            throw new RuntimeException(e);
        }
    }
}
