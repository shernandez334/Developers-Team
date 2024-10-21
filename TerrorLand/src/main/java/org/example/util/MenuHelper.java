package org.example.util;

import org.example.enums.Difficulty;
import org.example.enums.Material;
import org.example.enums.Theme;
import org.example.enums.Type;

import java.util.Arrays;

public class MenuHelper {
    private static final String [] TYPE_OPTIONS = {"1. ROOM", "2. DECOR_ITEM", "3. CLUE"};
    private static final String[] DIFFICULTY_OPTIONS = {"1.EASY", "2.MEDIUM", "3.HARD", "4.EPIC"};
    private static final String[] THEME_OPTIONS = {"1. SCI-FI", "2. MEDIEVAL", "3. SPACE"};
    private static final String[] MATERIAL_OPTIONS = {"1. PLASTIC", "2. PAPER", "3. STONE", "4. GLASS", "5. METAL"};

    public static int readSelection(String heading, String closing, String ... options){
        int selection;
        do {
            System.out.println(heading);
            Arrays.stream(options).forEach(System.out::println);
            selection = IOHelper.readInt(closing);
        }while (selection <= 0 || selection > options.length);
        return selection;
    }

    public static Type readTypeSelection(String message){
        int op = readSelection(message, ">", TYPE_OPTIONS);
        return Type.values()[op - 1];
    }

    public static Difficulty readDifficultySelection(String message) {
        int op = readSelection(message, ">", DIFFICULTY_OPTIONS);
        return Difficulty.values()[op - 1];
    }

    public static Theme readThemeSelection(String message){
        int op = readSelection(message, ">", THEME_OPTIONS);
        return Theme.values()[op - 1];
    }

    public static Material readMaterialSelection(String message){
        int op = readSelection(message, ">", MATERIAL_OPTIONS);
        return Material.values()[op - 1];
    }
}
