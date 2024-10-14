package org.example.dao.store;

import org.example.dao.element.RoomMySql;
import org.example.mysql.MySqlHelper;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.example.database.MySQL.getConnectionFormatted;

public class StoreValuesIntoTableMySql implements StoreValuesIntoTable{
    private static final Logger log = LoggerFactory.getLogger(StoreValuesIntoTableMySql.class);

    public void inputValuesIntoSQLTable(String elementTypeQuery) throws SQLException {
        Connection conn = getConnectionFormatted();
        try {
            Statement stmt = conn.createStatement();
            log.info("Executing SQL: {}", elementTypeQuery);
            stmt.executeUpdate(elementTypeQuery);
        } catch (SQLIntegrityConstraintViolationException e){
            log.error("Duplicate entry. Please check the element's name or ID. {}", e.getMessage());
        } catch (SQLException e){
            log.error("SQL execution error: {}", e.getMessage());
        }
    }
}
