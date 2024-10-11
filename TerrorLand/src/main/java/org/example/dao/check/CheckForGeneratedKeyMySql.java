package org.example.dao.check;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CheckForGeneratedKeyMySql implements CheckForGeneratedKey {

    public ResultSet checkForGeneratedKey(ResultSet setId) throws SQLException {
        if (!setId.next()) {
            throw new SQLException("No generated key returned.");
        }
        return setId;
    }
}
