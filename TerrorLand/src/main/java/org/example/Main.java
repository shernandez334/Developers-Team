package org.example;

import org.example.database.DatabaseFactory;
import org.example.database.FactoryProvider;
import org.example.enums.ProcessStatus;
import org.example.menu.MainMenu;
import org.example.services.InitializeResourcesService;

public class  Main {

    public static void main(String[] args) {

        InitializeResourcesService Setup = new InitializeResourcesService();
        if (Setup.runSetup() == ProcessStatus.EXIT) return;
        DatabaseFactory databaseFactory = FactoryProvider.getInstance().getDbFactory();
        Setup.initiateRewardsService(databaseFactory);
        MainMenu menu = new MainMenu(databaseFactory);
        menu.run();
    }
}