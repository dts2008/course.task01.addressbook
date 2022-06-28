package org.example.addressbook.application;

import org.example.addressbook.application.Common.DatabaseCache;
import org.example.addressbook.application.Common.Interface.*;
import org.example.addressbook.application.Controller.InputController;
import org.example.addressbook.application.Controller.MenuController;
import org.example.addressbook.application.Controller.OutputController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {
    @Bean
    public Output getOutput()
    {
        return new OutputController();
    }

    @Bean
    public Input getInput(Output output)
    {
        return new InputController(output);
    }

    @Bean
    public Cache getCache(ApplicationContext context)
    {
        return new DatabaseCache(context);
    }

    @Bean
    public MenuItems getMenuItems(Output output, Input input, Cache cache)
    {
        return new AppMenu(output, input, cache);
    }

//    Use annotation @Component for test
//    @Bean
//    public Menu getMenu(MenuItems appMenu, Output output, Input input)
//    {
//        return new MenuController(appMenu, output, input);
//    }
}
