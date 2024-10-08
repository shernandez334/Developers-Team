package org.example.dao;

public interface ElementDao {
    void createAnElement();
    String createElementRoom();
    String createElementDecoration();
    String createElementClue();
    int inputElementTableInfo(int elementType);
    String elementTableQuery(int elementType);
    void storeElementInStorage(int element_id);
    void deleteAnElement();
}
