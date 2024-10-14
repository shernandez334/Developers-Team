package org.example.dao.generate;

import org.example.dao.check.CheckForGeneratedKeyMySql;
import org.example.dao.get.GetElementId;
import org.example.dao.get.GetElementIdMySql;
import java.sql.*;
import static org.example.database.MySQL.getConnectionFormatted;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GenerateElementIdMySql implements GenerateElementId {
    private static final GenerateElementQuery query = new GenerateElementQueryMySql();
    private static final GetElementId getId = new GetElementIdMySql();
    private static final Logger log = LoggerFactory.getLogger(GenerateElementIdMySql.class);
    private static final CheckForGeneratedKeyMySql set = new CheckForGeneratedKeyMySql();

    @Override
    public int generateElementId(int elementType) throws SQLException{
        Connection conn = getConnectionFormatted();
        String elementSQLQuery = query.generateElementQuery(elementType);
        log.info("Generated SQL query for element table: {}", elementSQLQuery);
        PreparedStatement pstmt = conn.prepareStatement(elementSQLQuery, Statement.RETURN_GENERATED_KEYS);
        pstmt.executeUpdate();
        log.info("Element table successfully inserted into the SQL database.");
        ResultSet setId = set.checkForGeneratedKey(pstmt.getGeneratedKeys());
        log.info("Keys created Successfully.");
        return getId.getElementId(setId);
    }
}
