package org.example.dao;

import org.example.exceptions.ElementIdException;

public interface ClueDao {
    String createElementClue() throws ElementIdException;
}
