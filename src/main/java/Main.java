import Controller.*;

public class Main {
    public static void main(String[] args) {
        var output = new OutputController();
        var input = new InputController(output);
        var mainMenu = new MenuController();

        var appMenu = new AppMenu(mainMenu, output, input);


        mainMenu.Init(appMenu, output, input);

        mainMenu.Loop();

    }
}
