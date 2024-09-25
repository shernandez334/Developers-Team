package org.example.logic;

import org.example.database.Database;
import org.example.database.MySQL;
import org.example.exceptions.MySqlCredentialsException;
import org.example.util.Properties;

public class EscapeRoom {

    public void run() {
        try {
            initialSetup();
        } catch (MySqlCredentialsException e) {
            System.out.println(e.getMessage());;
        }
    }

    private void initialSetup() throws MySqlCredentialsException {
        Properties.createFileIfMissing();
        Database db = new MySQL();
        db.createIfMissing();
    }
}
