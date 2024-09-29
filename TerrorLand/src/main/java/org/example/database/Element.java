package org.example.database;

import org.example.enums.Difficulty;
import org.example.persistence.Room;
import org.example.util.Menu;
import static org.example.enums.Difficulty.checkDifficulty;
import static org.example.persistence.Room.createRoom;

public abstract class Element {
    public abstract String dataInfo();

    public static Element createAnElement(){
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
