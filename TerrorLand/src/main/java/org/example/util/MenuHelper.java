package org.example.util;

import org.example.enums.Difficulty;

import java.util.Arrays;

public class MenuHelper {
    private static final String[] DIFFICULTY_OPTIONS = {"1.EASY", "2.MEDIUM", "3.HARD", "4.EPIC"};

    public static int readSelection(String heading, String closing, String ... options){
        int selection;
        do {
            System.out.println(heading);
            Arrays.stream(options).forEach(System.out::println);
            selection = IOHelper.readInt(closing);
        }while (selection <= 0 || selection > options.length);
        return selection;
    }

    public static Difficulty readDifficultySelection(String message) {
        int op = MenuHelper.readSelection(message, ">", DIFFICULTY_OPTIONS);
        return Difficulty.values()[op - 1];
    }

}
