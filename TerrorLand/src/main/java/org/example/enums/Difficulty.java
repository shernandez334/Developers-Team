package org.example.enums;

import org.example.util.Menu;

import java.util.Scanner;

import static org.example.util.IO.readString;

public enum Difficulty {
    EASY, MEDIUM, HARD, EPIC;

    public static String getDifficultyLevel(String message) {
            String difficulty = readString(message).toUpperCase();
            /*try {
                System.out.println(difficulty);
            } catch (IllegalArgumentException e){
                System.out.println(difficulty + " is not a level of difficulty.");
            }*/
        return difficulty;
    }
}
