package org.example.dao;

import org.example.exceptions.ElementIdException;

public interface DecorationDao {
    String createElementDecoration() throws ElementIdException;
}
