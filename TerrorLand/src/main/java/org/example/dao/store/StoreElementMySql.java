package org.example.dao.store;

import org.example.util.IOHelper;
import java.sql.SQLException;

import static org.example.database.MySQL.inputValuesIntoSQLTable;

public class StoreElementMySql implements StoreElement {

    @Override
    public void storeElementInStorage(int element_id) throws SQLException {
        int quantity = IOHelper.readInt("Quantity: ");
        String query = "INSERT INTO stock_manager (element_id, total_quantity, available_quantity, deleted) " +
                "VALUES (" + element_id + ", " + quantity + ", " + quantity + ", " + 1 + ");";
        inputValuesIntoSQLTable(query);
    }
}