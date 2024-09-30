package org.example.database;

import org.example.exceptions.ExistingEmailException;
import org.example.exceptions.MySqlCredentialsException;
import org.example.exceptions.RunSqlFileException;
import org.example.logic.*;
import org.example.util.Properties;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.util.ArrayList;
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
                Connection connection = DriverManager.getConnection(url, user, password);
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
            Connection connection = getConnection(Properties.DB_NAME.getValue());
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
        Path path = Path.of(file);
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             BufferedReader input = Files.newBufferedReader(path.toRealPath())
        ) {

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


    public boolean createUser(User user) throws ExistingEmailException {

        try (Connection connection = getConnection(Properties.DB_NAME.getValue());
             Statement statement = connection.createStatement()) {
            String str = String.format("INSERT INTO user (name, email, password, role) VALUES('%s', '%s', '%s', '%s');",
                    user.getName(), user.getEmail(), user.getPassword(), user instanceof Player ? "player" : "admin");
            statement.execute(str);
            if (user instanceof Player) {
                Statement statement2 = connection.createStatement();
                str = "SELECT LAST_INSERT_ID();";
                ResultSet lastId = statement2.executeQuery(str);
                lastId.next();
                subscribePlayer(lastId.getInt(1));
                statement2.close();
            }
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

    public static boolean createTicket(User user, BigDecimal price){
        try (Connection connection = getConnection(Properties.DB_NAME.getValue());
             Statement statement = connection.createStatement()) {
            String str = String.format("INSERT INTO ticket (user_id, price, cashed) VALUES('%s', '%s', '%s');",
                    user.getId(), price, 0);
            statement.execute(str);
            return statement.getUpdateCount() == 1;
        } catch (SQLException | MySqlCredentialsException e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<Ticket> getTickets(User user, boolean onlyNotCashed){
        ArrayList<Ticket> tickets = new ArrayList<>();
        try (Connection connection = getConnection(Properties.DB_NAME.getValue());
             Statement statement = connection.createStatement()) {
            String str = String.format("SELECT ticket_id, cashed FROM ticket WHERE user_id = '%d';", user.getId());
            ResultSet result = statement.executeQuery(str);
            while (result.next()){
                if (onlyNotCashed){
                    if(!result.getBoolean("cashed")){
                        tickets.add(new Ticket(result.getInt("ticket_id")));
                    }
                }else {
                    tickets.add(new Ticket(result.getInt("ticket_id")));
                }
            }
            return tickets;
        } catch (SQLException | MySqlCredentialsException e) {
            throw new RuntimeException(e);
        }
    }

    public static void cashTicket(int ticketId) {
        try (Connection connection = getConnection(Properties.DB_NAME.getValue());
             Statement statement = connection.createStatement()) {
            String str = String.format("UPDATE ticket SET cashed = 1 WHERE ticket_id = %d;",
                    ticketId);
            statement.executeUpdate(str);
        } catch (SQLException | MySqlCredentialsException e) {
            throw new RuntimeException(e);
        }
    }

    public static BigDecimal getTotalIncome(){
        try (Connection connection = getConnection(Properties.DB_NAME.getValue());
             Statement statement = connection.createStatement()) {
            String str = "SELECT SUM(price) FROM ticket;";
            ResultSet result = statement.executeQuery(str);
            if (result.next()){
                return result.getBigDecimal(1);
            }else {
                return null;
            }
        } catch (SQLException | MySqlCredentialsException e) {
            throw new RuntimeException(e);
        }
    }

    public static void subscribePlayer(int playerId){
        try (Connection connection = getConnection(Properties.DB_NAME.getValue());
             Statement statement = connection.createStatement()) {
            String str = String.format("INSERT INTO subscription (user_id) VALUES (%d);", playerId);
            statement.executeUpdate(str);
        } catch (SQLException | MySqlCredentialsException e) {
            throw new RuntimeException(e);
        }
    }

    public static void unsubscribePlayer(int playerId){
        try (Connection connection = getConnection(Properties.DB_NAME.getValue());
             Statement statement = connection.createStatement()) {
            String str = String.format("DELETE FROM subscription WHERE user_id = %d;", playerId);
            statement.execute(str);
        } catch (SQLException | MySqlCredentialsException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean isSubscribed(int playerId){
        try (Connection connection = getConnection(Properties.DB_NAME.getValue());
             Statement statement = connection.createStatement()) {
            String str = String.format("SELECT * FROM subscription WHERE user_id = %d;", playerId);
            ResultSet result = statement.executeQuery(str);
            return result.next();
        } catch (SQLException | MySqlCredentialsException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean insertIntoDatabase(Storable storable){
        try (Connection connection = getConnection(Properties.DB_NAME.getValue());
             Statement statement = connection.createStatement()) {
            statement.execute(storable.insertString());
            return statement.getUpdateCount() == 1;
        } catch (SQLException | MySqlCredentialsException e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<Integer> retrieveIdsFromDatabase(String query){
        ArrayList<Integer> response = new ArrayList<>();
        try (Connection connection = getConnection(Properties.DB_NAME.getValue());
             Statement statement = connection.createStatement()) {
            ResultSet result = statement.executeQuery(query);
            while (result.next()){
                response.add(result.getInt(1));
            }
        } catch (SQLException | MySqlCredentialsException e) {
            throw new RuntimeException(e);
        }
        return response;
    }

    public static ArrayList<Integer> getSubscribers() {
        return MySQL.retrieveIdsFromDatabase("SELECT user_id FROM subscription;");
    }
}
