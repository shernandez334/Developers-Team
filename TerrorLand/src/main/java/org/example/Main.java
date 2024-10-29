package org.example;

import org.example.dao.DatabaseFactory;
import org.example.dao.FactoryProvider;
import org.example.enums.ProcessStatus;
import org.example.menu.MainMenu;
import org.example.services.InitializeResourcesService;

public class  Main {

    public static void main(String[] args) {

        InitializeResourcesService Setup = new InitializeResourcesService();
        if (Setup.runSetup() == ProcessStatus.EXIT) return;
        DatabaseFactory databaseFactory = FactoryProvider.getInstance().getDbFactory();
        Setup.setupRewards(databaseFactory);
        MainMenu menu = new MainMenu(databaseFactory);
        menu.run();
    }
}