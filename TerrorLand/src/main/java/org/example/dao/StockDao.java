package org.example.dao;

import org.example.factory.StockFactory;
import org.example.util.OutputBuilder;
import org.example.mysql.Queries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static org.example.mysql.MySqlHelper.getConnection;

public class StockDao implements StockFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(StockDao.class);
    private static final OutputBuilder OUTPUT_BUILDER = new OutputBuilder();

    public void displayTotalPriceOfAllElementsInRooms() {
        String query = Queries.buildCalculateStockValue(); // Ensure this retrieves the query above
        try (Connection conn = getConnection("escape_room");
             PreparedStatement psmt = conn.prepareStatement(query);
             ResultSet rs = psmt.executeQuery()) {
            if (rs.next()) {
                String totalPrice = OUTPUT_BUILDER.buildTotalPriceOutput(rs);
                System.out.println(totalPrice);
            }
        } catch (SQLException e) {
            LOGGER.error("Could not retrieve the total price for all elements in rooms: {}", e.getMessage());
        }
    }
}