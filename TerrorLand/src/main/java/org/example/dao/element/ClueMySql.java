package org.example.dao.element;

import org.example.dao.generate.GenerateElementIdMySql;
import org.example.dao.store.StoreElementMySql;
import org.example.enums.Theme;
import org.example.util.MenuHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class ClueMySql extends StoreElementMySql implements Clue {
    private final GenerateElementIdMySql element = new GenerateElementIdMySql();
    private static final Logger log = LoggerFactory.getLogger(ClueMySql.class);

    @Override
    public String createElementClue() throws SQLException{
        int element_id = element.generateElementId(2);
        storeElementInStorage(element_id);
        Theme theme = MenuHelper.readThemeSelection("Choose a theme: ");
        return "INSERT INTO clue (element_id, theme) "
                + "VALUES (" + element_id + ", "  + theme + "');";
    }
}
