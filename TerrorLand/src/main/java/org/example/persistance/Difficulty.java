package org.example.persistance;

import org.example.util.Menu;

public class Difficulty {

    public static String getDifficultyLevel(String message) {
        String dif = "";
        int op = Menu.readSelection(message, ">",
                "1. EASY", "2. MEDIUM", "3. HARD", "4. EPIC");
        switch (op){
            case 1 -> dif = "EASY";
            case 2 -> dif = "MEDIUM";
            case 3 -> dif = "HARD";
            case 4 -> dif = "EPIC";
            default -> dif = "Wrong Input.";
        }
        return dif;
    }
}
