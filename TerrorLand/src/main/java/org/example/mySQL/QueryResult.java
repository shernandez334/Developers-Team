package org.example.mySQL;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class QueryResult implements AutoCloseable{

    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    public QueryResult(Connection connection, Statement statement, ResultSet resultSet) {
        this.connection = connection;
        this.statement = statement;
        this.resultSet = resultSet;
    }

    public ResultSet getResultSet() {
        return resultSet;
    }

    @Override
    public void close() throws SQLException {
        if (connection != null) connection.close();
        if (statement != null) statement.close();
        if (resultSet != null) resultSet.close();
    }
}
