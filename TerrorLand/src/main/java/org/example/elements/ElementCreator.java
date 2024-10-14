package org.example.elements;

import org.example.entities.Element;
import org.example.entities.Room;
import org.example.enums.Difficulty;
import org.example.util.MenuHelper;

public class ElementCreator {

    public void createElements(){
        Element elem = Element.createAnElement();
        int op = MenuHelper.readSelection("What element would you like to create?", ">",
                "1. Room", "2. Decoration", "3. Clue");
        switch (op){
            case 1 -> createElementRoom(elem);
            //case 2 -> decoration.createElementDecoration();
            //case 3 -> clueDaoMySql.createElementClue();
        }
    }

    public void createElementRoom(Element elem){
        Difficulty difficulty = MenuHelper.readDifficultySelection("Choose a level of difficulty:");
        Room room = new Room(elem.getElement_id(), difficulty);
    }
}

