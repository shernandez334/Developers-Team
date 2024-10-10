package org.example.dao;

import org.example.exceptions.MySqlEmptyResultSetException;

import java.math.BigDecimal;

import static org.example.dao.GenericMethodsMySQL.retrieveSingleValueFromDatabase;

public class AdminDaoMySql {

    public BigDecimal getTotalIncome() {
        String sql = "SELECT SUM(price) FROM ticket;";
        try {
            return retrieveSingleValueFromDatabase(sql, BigDecimal.class);
        } catch (MySqlEmptyResultSetException e) {
            throw new RuntimeException(String.format("The query '%s' didn't yield a result.%n", sql));
        }
    }

}
