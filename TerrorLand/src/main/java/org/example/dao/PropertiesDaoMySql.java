package org.example.dao;

import org.example.mysql.MySqlHelper;
import org.example.exceptions.MySqlEmptyResultSetException;
import org.example.exceptions.MySqlPropertyNotFoundException;

import java.math.BigDecimal;
import java.sql.SQLIntegrityConstraintViolationException;

import static org.example.mysql.MySqlHelper.retrieveSingleValueFromDatabase;

public class PropertiesDaoMySql implements PropertiesDao {
    @Override
    public BigDecimal getTicketPrice() {
        String response;
        try {
            response = retrieveSingleValueFromDatabase(
                    "SELECT value FROM persistent_property WHERE name = 'ticket_price';",String.class);
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

    @Override
    public void setTicketPrice(String price){
        String sql = String.format("UPDATE persistent_property SET value = %s WHERE name = 'ticket_price';", price);
        try {
            MySqlHelper.createStatementAndExecute(sql);
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new RuntimeException(e);
        }
    }
}
