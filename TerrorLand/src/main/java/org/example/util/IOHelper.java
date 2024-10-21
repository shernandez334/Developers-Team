package org.example.util;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;


public class IOHelper {

    private static final Scanner input;

    static {
        input = new Scanner(System.in);
    }

    public static byte readByte(String message) {
        System.out.println(message);

        byte b = ' ';
        boolean validInput = false;
        while (!validInput) {
            try {
                b = input.nextByte();
                validInput = true;
            } catch (InputMismatchException err) {
                System.out.println("Invalid Input." + "\n" + message);
                input.next();
            }
        }
        return b;
    }

    public static int readInt(String message) {
        boolean success = false;
        int myInt = 0;
        do {
            try {
                System.out.print(message);
                myInt = input.nextInt();
                success = true;
            }catch(InputMismatchException e) {
                System.out.println("Format error.");
                input.nextLine();
            }
        }while(!success);
        input.nextLine();
        return myInt;
    }

    public static double readDouble(String message) {
        boolean success = false;
        double myDouble = 0;
        do {
            try {
                System.out.print(message);
                myDouble = input.nextDouble();
                success = true;
            }catch(InputMismatchException e) {
                System.out.println("Format error.");
                input.nextLine();
            }
        }while(!success);
        input.nextLine();
        return myDouble;
    }

    public static String readString(String message){
        String myString;
        do {
            System.out.print(message);
            myString = input.nextLine();
        }while(myString.isBlank());
        return myString;
    }

    public static char readChar(String message){
        while (true) {
            String input = readString(message);
            if (input.length() == 1) {
                return input.charAt(0);
            }
        }
    }

    public static String indentText(String rawMessage, String indent) {
        StringBuilder message = new StringBuilder();
        Arrays.stream(rawMessage
                .split("\n"))
                .forEach(s -> {message.append(indent); message.append(s);message.append("\n");}
                );
        return message.toString();
    }
}