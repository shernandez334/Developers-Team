package org.example.dao;

import org.example.exceptions.ElementIdException;

public interface RoomDao {
    String createElementRoom() throws ElementIdException;
}
