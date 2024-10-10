package org.example.dao;

import java.math.BigDecimal;

public interface PropertiesDao {
    BigDecimal getTicketPrice();

    void setTicketPrice(String price);
}
