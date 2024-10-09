package org.example.dao;

import static org.example.database.MySQL.inputDataInfo;

public class StoreElementDaoMySql implements StoreElementDao{

    @Override
    public void storeElementInStorage(int element_id) {
        String query = "INSERT INTO stock_manager (element_id, enabled, available) " +
                "VALUES ('" + element_id + "', '" + 1 + "', '" + 1 + "');";
        inputDataInfo(query);
    }
}
