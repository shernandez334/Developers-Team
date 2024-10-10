package org.example.dao;

import org.example.exceptions.ElementIdException;

public interface GenerateElementIdDao {
    int generateElementId(int elementType) throws ElementIdException;
}
