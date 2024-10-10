package org.example.dao;

import org.example.enums.DefaultProperties;
import org.example.enums.Properties;
import org.example.exceptions.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class GenericMethodsMySQL {

    public static Connection getConnection(String dbName) throws SQLException {
        Connection connection;
        String url = Properties.getProperty("db.url") + dbName;
        String user = Properties.getProperty("db.user");
        String password = Properties.getProperty("db.password");

        try {
            connection = DriverManager.getConnection(url, user, password);
            return connection;
        } catch (SQLException e) {
            System.out.printf("Error connecting to '%s' as '%s'.%n", url, user);
            throw e;
        }
    }

    public static Connection getConnection() throws SQLException {
        return getConnection("");
    }

    public static void executeSqlFile(String filePath) {
        Path path = Path.of(filePath);
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             BufferedReader input = Files.newBufferedReader(path.toRealPath())
        ) {
            StringBuilder sql = new StringBuilder();
            input.lines().filter(s -> !s.isEmpty() && !s.startsWith("--")).forEach(sql::append);

            Arrays.stream(sql.toString().split(";")).forEach(s -> {
                try {
                    System.out.println(s);
                    statement.execute(s);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });

        } catch (SQLException e) {
            throw new RuntimeException("Unable to run the script: MySQL error.", e);
        } catch (IOException e) {
            throw new RunSqlFileException("Unable to execute the script: error reading the '.sql' file.", e);
        }
    }

    public static <T> T retrieveSingleValueFromDatabase(String sql, Class<T> type) throws MySqlEmptyResultSetException {
        try (ResultSet result = createStatementAndExecuteQuery(sql)) {
            if (result.next()){
                return type.cast(result.getObject((1)));
            }else {
                throw new MySqlEmptyResultSetException("Query resulted in an empty ResultSet.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static int createStatementAndExecute(String sql) throws SQLIntegrityConstraintViolationException {
        try (Connection connection = getConnection(DefaultProperties.DB_NAME.getValue());
             Statement statement = connection.createStatement()) {
            boolean noResultSet = statement.execute(sql);
            if (noResultSet){

            }
            return getLastInsertedId();
        } catch (SQLIntegrityConstraintViolationException e){
            throw e;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ResultSet createStatementAndExecuteQuery(String sql){
        try (Connection connection = getConnection(DefaultProperties.DB_NAME.getValue());
             Statement statement = connection.createStatement()) {
            return statement.executeQuery(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> List<T> retrieveSingleColumnFromDatabase(String sql, Class<T> type){
        List<T> response = new ArrayList<>();
        try (ResultSet result = createStatementAndExecuteQuery(sql)) {
            while (result.next()){
                response.add(type.cast(result.getObject(1)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return response;
    }

    public static <T> List<List<Object>> retrieveMultipleColumnsFromDatabase(String sql, String[] types) {
        List<List<Object>> response = new ArrayList<>();
        try (ResultSet result = createStatementAndExecuteQuery(sql)) {
            while (result.next()){
                response.add(new ArrayList<>());
                for (int i = 0; i < types.length; i++){
                    response.getLast().add(Class.forName(types[i]).cast(result.getObject(i +1)));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(String.format("Class within '%s' not found.%n", Arrays.toString(types)));
        }
        return response;
    }

    public static int getLastInsertedId() {
        String sql = "SELECT LAST_INSERT_ID();";
        try {
            return retrieveSingleValueFromDatabase(sql, Integer.class);
        } catch (MySqlEmptyResultSetException e) {
            throw new RuntimeException(e);
        }
    }

    public static int getLastInsertedId(Statement statement) {
        String sql = "SELECT LAST_INSERT_ID();";
        try (ResultSet result = statement.executeQuery(sql);) {
            if (result.next()){
                return result.getInt(1);
            }else {
                return 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
