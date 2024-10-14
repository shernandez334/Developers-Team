package org.example.dao;

import org.example.exceptions.ElementIdException;
import java.sql.*;
import static org.example.mysql.MySqlHelper.getConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GenerateElementIdDaoMySql implements GenerateElementIdDao{
    private static final Logger log = LoggerFactory.getLogger(GenerateElementIdDaoMySql.class);
    private static final GenerateElementQueryDao query = new GenerateElementQueryDaoMySql();

    @Override
    public int generateElementId(int elementType) throws ElementIdException{
        int element_id = 0;
        String insertElementSQL = query.generateElementQuery(elementType);
        try (Connection conn = getConnection("escape_room");
             PreparedStatement pstmt = conn.prepareStatement(insertElementSQL, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    element_id = generatedKeys.getInt(1);
                }
            }
        } catch (SQLException e){
            throw new ElementIdException(e);
        }
        return element_id;
    }
}
