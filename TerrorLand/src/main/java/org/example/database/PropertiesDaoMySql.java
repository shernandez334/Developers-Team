package org.example.database;

import org.example.dao.GenericMethodsMySQL;
import org.example.exceptions.MySqlEmptyResultSetException;
import org.example.exceptions.MySqlPropertyNotFoundException;

import java.math.BigDecimal;
import java.sql.SQLIntegrityConstraintViolationException;

import static org.example.dao.GenericMethodsMySQL.retrieveSingleValueFromDatabase;

public class PropertiesDaoMySql {
    public BigDecimal getTicketPrice() {
        String response = "";
        try {
            response = retrieveSingleValueFromDatabase(
                    "SELECT value FROM property WHERE name = 'ticket_price';",String.class);
        } catch (MySqlEmptyResultSetException e) {
            throw new MySqlPropertyNotFoundException("Error: ticket-price not found.", e);
        }
        double price;
        try{
            price = Double.parseDouble(response);
        }catch (NumberFormatException e){
            price = Double.parseDouble(response.replace(",", "."));
        }
        return BigDecimal.valueOf(price);
    }

    public void setTicketPrice(String price){
        String sql = String.format("UPDATE property SET value = %s WHERE name = 'ticket_price';", price);
        try {
            GenericMethodsMySQL.createStatementAndExecute(sql);
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new RuntimeException(e);
        }
    }
}
