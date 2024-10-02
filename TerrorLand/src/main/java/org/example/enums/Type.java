package org.example.enums;

import static org.example.util.IO.readString;

public enum Type {
    ROOM, CLUE, DECOR_ITEM;

    public static Type checkType(){
        Type t = null;
        while (t == null){
            String type = readString("Please choose a level of difficulty:\n" +
                    "ROOM\n" + "CLUE\n" + "DECOR_ITEM");
            try {
                t = Type.valueOf(type.toUpperCase());
            } catch (IllegalArgumentException e){
                System.out.println(type + " is not a level of difficulty.");
            }
        }
        return t;
    }
}
