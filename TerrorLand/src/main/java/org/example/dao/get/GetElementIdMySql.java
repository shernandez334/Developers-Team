package org.example.dao.get;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GetElementIdMySql{

    public int getElementId(ResultSet setId) throws SQLException {
        int element_id;
        element_id = setId.getInt(1);
        if (setId.wasNull()) {
            throw new SQLException("Generated key was null.");
        }
        return element_id;
    }
}