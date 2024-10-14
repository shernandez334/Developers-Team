package org.example.dao.store;

import java.sql.SQLException;

public interface StoreElement {
    void storeElementInStorage(int element_id) throws SQLException;
}
