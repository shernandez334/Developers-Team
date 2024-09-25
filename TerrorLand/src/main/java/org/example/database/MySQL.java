package org.example.database;

import org.example.exceptions.MySqlCredentialsException;
import org.example.exceptions.RunSqlFileException;
import org.example.util.Properties;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
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

    public static Connection getConnection(String dbName) throws SQLException {

        if (MySQL.connection != null && MySQL.connection.isValid(0)){
            return MySQL.connection;
        }else {
            String url = Properties.getProperty("db.url") + dbName;
            String user = Properties.getProperty("db.user");
            String password = Properties.getProperty("db.password");

            try {
                Connection connection = DriverManager.getConnection(url, user, password);
                System.out.printf("Connected to %s.", dbName.isEmpty() ? "DB" : dbName);
                return connection;

            }catch (SQLException e){
                System.out.printf("Error connecting to '%s' as user '%s' and password '%s'%n", url, user, password);
                throw e;
            }
        }


    }

    public static Connection getConnection() throws SQLException {
        return getConnection("");
    }

    public void createIfMissing() throws MySqlCredentialsException {

        try {
            Connection connection = getConnection(Properties.DB_NAME.getValue());
        } catch (SQLException e) {
            if (e.getErrorCode() == 1045){
                throw new MySqlCredentialsException(
                        "Error connecting to MySQL server. Modify the credentials at '%s'%n",e);
            } else if (e.getErrorCode() == 1049){
                System.out.println("Unknown database -> Creating Database...");
                executeSqlFile(Properties.SQL_SCHEMA_CREATION_FILE.getValue());
            }else {
                throw new RuntimeException(e);
            }
        }
    }

    private static void executeSqlFile(String file) {
        Path path = Path.of(file);
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             BufferedReader input = Files.newBufferedReader(path.toRealPath())
        ){

            StringBuilder sql = new StringBuilder();
            input.lines().filter(s->!s.isEmpty() && !s.matches("^--.*")).forEach(sql::append);

            Arrays.stream(sql.toString().split(";")).forEach(s-> {
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


}
