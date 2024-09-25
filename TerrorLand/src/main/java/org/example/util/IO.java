package org.example.util;

import java.util.InputMismatchException;
import java.util.Scanner;


public class IO {

    private static final Scanner input;

    static {
        input = new Scanner(System.in);
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
        boolean success = false;
        String myString =  "";
        do {
            try {
                System.out.print(message);
                myString = input.nextLine();
                success = true;
            }catch(Exception e) {
                System.out.println("Error");
                input.nextLine();
            }
        }while(!success);
        return myString;
    }

}