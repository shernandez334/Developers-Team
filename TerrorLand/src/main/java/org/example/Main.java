package org.example;

import org.example.dao.FactoryProvider;
import org.example.enums.ProcessStatus;
import org.example.menu.MainMenu;
import org.example.services.InitializeResourcesService;

public class  Main {

    public static void main(String[] args) {

        InitializeResourcesService Setup = new InitializeResourcesService();
        if (Setup.runSetup() == ProcessStatus.EXIT) return;

        MainMenu menu = new MainMenu(FactoryProvider.getInstance().getDbFactory());
        menu.run();

    }

}