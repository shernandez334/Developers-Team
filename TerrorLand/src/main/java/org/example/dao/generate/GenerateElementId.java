package org.example.dao.generate;

import org.example.exceptions.ElementIdException;

import java.sql.SQLException;

public interface GenerateElementId {
    int generateElementId(int elementType) throws SQLException;
}
