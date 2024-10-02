package org.example.persistence;

import org.example.enums.Difficulty;
import org.example.enums.Type;
import org.example.util.Menu;
import static org.example.enums.Difficulty.getDifficultyLevel;
import static org.example.enums.Type.getElementType;
import static org.example.persistence.Clue.createClue;
import static org.example.persistence.Decoration.createDecoration;
import static org.example.persistence.Room.createRoom;
import static org.example.util.IO.*;

public class ElementCreator {
    private static final String ELEMENT_TYPE_PROMPT = "Please choose an element type:\nROOM, CLUE, DECOR_ITEM\n>";
    private static final String DIFFICULTY_LEVEL_PROMPT = "Please choose a difficulty level:\nEASY, MEDIUM, HARD, EPIC\n>";

    public static Element createAnElement(){
        Element e = null;
        int op = Menu.readSelection("What element would you like to create?", ">",
                "1. Room", "2. Decoration", "3. Clue");
        switch (op){
            case 1 -> e = createElementRoom();
            case 2 -> e = createElementDecoration();
            case 3 -> e = createElementClue();
        }
        return e;
    }

    public static Element createElementRoom(){
        String nameElem = readString("Name of the room:\n>");
        Type type = getElementType(ELEMENT_TYPE_PROMPT);
        int quantity = readInt("Quantity of the element:\n>");
        double price = readFloat("Price of the element:\n>");
        Difficulty difficulty = getDifficultyLevel(DIFFICULTY_LEVEL_PROMPT);
        String theme = readString("Theme of the element\n>");

        return createRoom(nameElem, type, quantity, price, theme, difficulty);
    }

    public static Element createElementDecoration(){
        String nameElem = readString("Name of the decoration:");
        Type type = getElementType(ELEMENT_TYPE_PROMPT);
        int quantity = readInt("Quantity of the element:");
        double price = readFloat("Price of the element:");
        String theme = readString("Theme of the element:");
        String material = readString("Material of the element:");

        return createDecoration(nameElem, type, quantity, price, theme, material);
    }

    public static Element createElementClue(){
        String nameElem = readString("Clue:");
        Type type = getElementType(ELEMENT_TYPE_PROMPT);
        int quantity = readInt("Quantity of the element:");
        double price = readFloat("Price of the element:");
        String theme = readString("Theme of the element:");

        return createClue(nameElem, type, quantity, price, theme);
    }
}
