package org.example.addressbook.application.Controller;

import lombok.RequiredArgsConstructor;
import org.example.addressbook.application.Common.Interface.*;
import org.example.addressbook.application.Common.Tools.Pair;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.util.Objects.requireNonNull;

@Component
@RequiredArgsConstructor
public class MenuController implements Menu {

    private boolean IsStop = false;

    private HashMap<Character, Pair<MenuItem, Method>> menuMethods = new HashMap<Character, Pair<MenuItem, Method>>();

    private List<MenuItem> menuItemList = new ArrayList<>();

    private final MenuItems appMenu;

    private final Output output;

    private final Input input;

    public void Init() {
        appMenu.SetMenuController(this);
        Class<?> objectClass = requireNonNull(appMenu).getClass();
        int maxId = 0;

        for (Method method : objectClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(MenuItem.class)) {
                method.setAccessible(true);

                var item = method.getAnnotation(MenuItem.class);

                menuMethods.put(item.Key(), Pair.of(item, method));
                menuItemList.add(item);
            }
        }

        menuItemList.sort((a, b) -> Integer.compare(a.Order(), b.Order()));
    }

    @SneakyThrows
    public void Loop() {
        ShowMenu();

        IsStop = false;

        while (!IsStop) {
            char key = input.GetChar("Command: ");

            var item = menuMethods.get(key);

            if (item == null) {
                output.printLn("Wrong command.");
                continue;
            }

            output.printLn(item.getLeft().Name());

            item.getRight().invoke(appMenu);
        }
    }

    public void Exit()
    {
        IsStop = true;
    }

    public void ShowMenu()
    {
        int i = 1;
        for (var item: menuItemList)
        {
            output.format(" %d) %s (%c)\r\n", i , item.Name(), item.Key());
            ++i;
        }
    }
}
