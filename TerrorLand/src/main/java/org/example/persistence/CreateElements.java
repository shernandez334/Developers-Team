package org.example.persistence;

import org.example.database.Element;
import org.example.enums.Difficulty;
import org.example.enums.Type;
import org.example.util.Menu;
import static org.example.enums.Difficulty.checkDifficulty;
import static org.example.enums.Type.checkType;
import static org.example.persistence.Room.createRoom;
import static org.example.util.IO.*;

public class CreateElements {

    public static Element createAnElement(){
        String nameElem = readString("Name of the element:");
        Type type = checkType();
        int quantity = readInt("Quantity of the element:");
        double price = readFloat("Price of the element:");

        Element e = null;
        int op = Menu.readSelection("What element would you like to create?", ">",
                "1. Room", "2. Decoration", "3. Clue");
        switch (op){
            case 1 -> {

                Difficulty difficulty = checkDifficulty();
                e = createRoom(difficulty);
            }
        }
        return e;
    }
}
