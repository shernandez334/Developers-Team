package org.example.factory;

import org.example.enums.Difficulty;
import org.example.enums.Material;
import org.example.enums.Theme;
import org.example.enums.Type;

public interface DatabaseInputFactory {
    void inputElementIntoTable(String name, double price, Type type);
    void inputDecorationIntoTable(int element_id, Material material);
    void inputClueIntoTable(int element_id, Theme theme);
    void inputRoomIntoTable(String name, Difficulty difficulty, int deleted);
    void inputRoomHasElementIntoTable(int room_id, int element_id, int quantity);
}
