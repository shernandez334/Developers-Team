package org.example.dao.check;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface CheckForGeneratedKey {
    ResultSet checkForGeneratedKey(ResultSet id) throws SQLException;
}
