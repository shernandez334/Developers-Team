package org.example.dao;

public interface ElementDao {
    void createAnElement();
    String createElementDecoration();
    String createElementClue();
    int inputElementTableInfo(int elementType);
    String elementTableQuery(int elementType);
    void deleteAnElement();
}
