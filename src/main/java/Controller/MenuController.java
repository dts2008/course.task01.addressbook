package Controller;

import Common.Interface.*;
import Common.Tools.Pair;
import lombok.SneakyThrows;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class MenuController implements Menu {

    public static final String ANSI_YELLOW = "\u001B[33m";

    public static final String ANSI_RESET = "\u001B[0m";

    public static final String ANSI_CLEAR = "\033[H\033[2J";

    public static final char ANSI_CLS = 0x1B;

    public static final int DEF_CURSOR_ROW = 0;

    public static final int DEF_CURSOR_COLUMN = 0;

    private boolean IsStop = false;

    private int activeMenuItem = 0;

    private HashMap<Character, Pair<MenuItem, Method>> menuMethods = new HashMap<Character, Pair<MenuItem, Method>>();

    private List<MenuItem> menuItemList = new ArrayList<>();

    private Object appMenu;

    private Output output;

    private Input input;

    public void Init(Object menuObject, Output output, Input input) {
        this.output = output;
        this.input = input;

        appMenu = menuObject;

        Class<?> objectClass = requireNonNull(menuObject).getClass();
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
