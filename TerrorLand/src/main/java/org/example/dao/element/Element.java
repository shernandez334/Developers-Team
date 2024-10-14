package org.example.dao.element;

import java.sql.SQLException;

public interface Element {
    void createAnElement() throws SQLException;
    void deleteAnElement();
}
