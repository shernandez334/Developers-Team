package org.example.enums;

import org.example.util.Menu;

import java.util.Scanner;

public enum Difficulty {
    EASY, MEDIUM, HARD, EPIC;

    public static Difficulty checkDifficulty() {
        Difficulty d = null;
        Scanner sc = new Scanner(System.in);

        while (d == null) {
            System.out.println("Please choose a level of difficulty:\n" +
                    "EASY\n" + "MEDIUM\n" + "HARD\n" + "EPIC");
            String dif = sc.next();
            try {
                d = Difficulty.valueOf(dif.toUpperCase());
            } catch (IllegalArgumentException e){
                System.out.println(dif + " is not a level of difficulty.");
            }
        }
        return d;
    }
}
