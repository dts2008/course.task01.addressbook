package org.example.addressbook.application;

import org.example.addressbook.application.Common.DatabaseCache;
import org.example.addressbook.application.Common.Interface.Menu;
import org.example.addressbook.application.Controller.InputController;
import org.example.addressbook.application.Controller.MenuController;
import org.example.addressbook.application.Controller.OutputController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");

        var mainMenu = context.getBean(Menu.class);

        mainMenu.Init();
        mainMenu.Loop();

    }
}
