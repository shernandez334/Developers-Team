package org.example.factory;

import org.example.entities.Clue;
import org.example.entities.Decoration;
import org.example.entities.Element;
import org.example.enums.Material;
import org.example.enums.Theme;
import org.example.enums.Type;
import org.example.util.MenuHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static org.example.mysql.MySqlHelper.getConnection;
import static org.example.util.IOHelper.readDouble;
import static org.example.util.IOHelper.readString;

public class ElementFactoryCreatorMySql implements ElementFactoryCreator {
    private static final Logger LOGGER = LoggerFactory.getLogger(ElementFactoryCreatorMySql.class);
    private static final DatabaseInputFactorySQL DATABASEINPUT = new DatabaseInputFactorySQL();

    @Override
    public Element createAnElement(Type type){
        String name = readString(String.format("Name of the %s:\n>", type));
        double price = readDouble(String.format("Price of the %s:\n>", type));
        DATABASEINPUT.inputElementIntoTable(name, price, type);
        int element_id = getCurrentElementId();
        return new Element(element_id, name, price, type);
    }

    @Override
    public Decoration createDecoration(){
        Element elem = createAnElement(Type.DECORATION);
        Material material = MenuHelper.readMaterialSelection("Material the decoration is made of: ");
        return new Decoration(elem.getElementId(), material);
    }

    @Override
    public Clue createClue(){
        Element elem = createAnElement(Type.CLUE);
        Theme theme = MenuHelper.readThemeSelection("Theme of the clue: ");
        return new Clue(elem.getElementId(), theme);
    }

    @Override
    public int getCurrentElementId() {
        int currentElementId = 0;
        String sql = "SELECT element_id FROM element ORDER BY element_id DESC LIMIT 1";
        try (Connection conn = getConnection("escape_room");
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            rs.next();
            currentElementId = rs.getInt("element_id");
        } catch (SQLException e) {
            LOGGER.error("Couldn't get the next element_id number: {}", e.getMessage());
        }
        return currentElementId;
    }
}