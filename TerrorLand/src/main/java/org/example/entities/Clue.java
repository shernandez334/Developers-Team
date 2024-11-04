package org.example.entities;

import org.example.enums.Theme;

public class Clue {
    private final int element_id;
    private final Theme theme;

    public Clue(int element_id, Theme theme){
        this.element_id = element_id;
        this.theme = theme;
    }

    public int getElementId(){
        return this.element_id;
    }
    public Theme getTheme(){
        return this.theme;
    }
}
