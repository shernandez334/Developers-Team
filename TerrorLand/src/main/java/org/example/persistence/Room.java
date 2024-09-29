package org.example.persistence;

import org.example.database.Database;
import org.example.database.Element;
import org.example.enums.Difficulty;
import java.util.ArrayList;
import java.util.List;

public class Room extends Element {
    private Difficulty diffLevel;

    public Room(Difficulty diffLevel){
        this.diffLevel = diffLevel;
    }

    public static Room createRoom(Difficulty diffLevel){
        return new Room(diffLevel);
    }

    @Override
    public String dataInfo() {
        return "INSERT INTO room (difficulty) VALUES ('" + this.diffLevel + "')";
    }
}
