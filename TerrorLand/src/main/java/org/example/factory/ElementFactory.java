package org.example.factory;

import org.example.entities.Clue;
import org.example.entities.Decoration;
import org.example.entities.Element;
import org.example.enums.Type;

public interface ElementFactory {
    Element createAnElement(Type type);
    Decoration createDecoration();
    Clue createClue();
    int getCurrentElementId();
}