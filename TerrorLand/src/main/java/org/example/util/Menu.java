package org.example.util;

import java.util.Arrays;

public class Menu {

    public static int readSelection(String heading, String closing, String ... options){
        int selection;
        do {
            System.out.println(heading);
            Arrays.stream(options).forEach(System.out::println);
            selection = IO.readInt(closing);
        }while (selection <= 0 || selection > options.length);
        return selection;
    };

}
