package org.example.logic;

import org.example.database.Database;
import org.example.database.MySQL;
import org.example.util.Properties;

public class EscapeRoom {

    public void run() {
        initialSetup();
    }

    private void initialSetup() {
        Properties.createFileIfMissing();
        Database db = new MySQL();
        db.createIfMissing();
    }
}
