package org.example.dao;

import org.example.database.MySQL;
import org.example.exceptions.MySqlEmptyResultSetException;
import org.example.exceptions.MySqlPropertyNotFoundException;

import java.math.BigDecimal;

public class PropertiesDaoMySql {
    public BigDecimal getTicketPrice() {
        String response = "";
        try {
            response = MySQL.retrieveSingleValueFromDatabase(
                    "SELECT value FROM property WHERE name = 'ticket_price';",String.class);
        } catch (MySqlEmptyResultSetException e) {
            throw new MySqlPropertyNotFoundException("Error: ticket-price not found.", e);
        }
        return BigDecimal.valueOf(Double.parseDouble(response));
    }

    public void setTicketPrice(String price){
        MySQL.executeQuery(String.format("UPDATE property SET value = %s WHERE name = 'ticket_price';", price));
    }
}
