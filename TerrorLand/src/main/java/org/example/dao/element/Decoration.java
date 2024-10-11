package org.example.dao.element;

import org.example.exceptions.ElementIdException;

public interface Decoration {
    String createElementDecoration() throws ElementIdException;
}
