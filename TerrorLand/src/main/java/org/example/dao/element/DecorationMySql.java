package org.example.dao.element;

import org.example.dao.generate.GenerateElementIdMySql;
import org.example.dao.store.StoreElementMySql;
import org.example.enums.Material;
import org.example.exceptions.ElementIdException;
import org.example.util.MenuHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;


public class DecorationMySql extends StoreElementMySql implements Decoration {
    private final GenerateElementIdMySql element = new GenerateElementIdMySql();

    public String createElementDecoration() throws SQLException {
        int element_id = element.generateElementId(3);
        storeElementInStorage(element_id);
        Material material = MenuHelper.readMaterialSelection("Choose the material for the element: ");
        return "INSERT INTO decor_item (element_id, material) " +
                "VALUES (" + element_id + ", " + material + "');";
    }
}
