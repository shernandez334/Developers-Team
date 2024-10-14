package org.example.dao.store;

import java.sql.SQLException;

public interface StoreValuesIntoTable {
    void inputValuesIntoSQLTable(String elementTypeQuery) throws SQLException;
}
