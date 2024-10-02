package org.example.enums;

import org.example.util.Menu;

import java.util.Scanner;

import static org.example.util.IO.readString;

public enum Difficulty {
    EASY, MEDIUM, HARD, EPIC;

    public static Difficulty getDifficultyLevel(String message) {
        Difficulty d = null;
        while (d == null) {
            String difficulty = readString(message);
            try {
                d = Difficulty.valueOf(difficulty.toUpperCase());
            } catch (IllegalArgumentException e){
                System.out.println(difficulty + " is not a level of difficulty.");
            }
        }
        return d;
    }
}
