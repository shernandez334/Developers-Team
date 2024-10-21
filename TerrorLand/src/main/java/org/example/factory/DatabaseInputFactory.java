package org.example.factory;

import org.example.entities.Element;
import org.example.enums.Difficulty;
import org.example.enums.Material;
import org.example.enums.Theme;
import org.example.enums.Type;

public interface DatabaseInputFactory {
    void inputElementIntoTable(Element elem);
    void inputDecorationIntoTable(int element_id, Material material);
    void inputClueIntoTable(int element_id, Theme theme);
    void inputRoomIntoTable(String name, Difficulty difficulty);
    void inputRoomHasElementIntoTable(int room_id, int element_id, int quantity);
}
