package org.example.dao.element;

import org.example.exceptions.ElementIdException;

import java.sql.SQLException;

public interface Room {
    String createElementRoom() throws SQLException;
}
