package org.example.dao.get;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface GetElementId {
    int getElementId(ResultSet setId) throws SQLException;
}
