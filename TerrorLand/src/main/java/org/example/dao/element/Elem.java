package org.example.dao.element;

import java.sql.SQLException;

public interface Elem {
    void createAnElement() throws SQLException;
    void deleteAnElement();
}
