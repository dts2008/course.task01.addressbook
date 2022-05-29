import Common.DatabaseCache;
import Common.Interface.Cache;
import Controller.*;

public class Main {
    public static void main(String[] args) {
        var output = new OutputController();
        var input = new InputController(output);
        var mainMenu = new MenuController();
        var cache = new DatabaseCache();

        var appMenu = new AppMenu(mainMenu, cache, output, input);



        mainMenu.Init(appMenu, output, input);

        mainMenu.Loop();

    }
}
