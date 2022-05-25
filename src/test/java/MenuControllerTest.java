import Common.Interface.*;
import Controller.MenuController;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

public class MenuControllerTest {

    @Test
    public void commandTest()
    {
        OutputTest output = new OutputTest();
        InputTest input = new InputTest('m');

        var menu = new MenuController();
        var testMenu = new TestMenu(menu, output, input);


        menu.Init(testMenu, output, input);
        menu.Loop();

        assertEquals(output.getString(), " 1) Menu (m)\r\n");
    }

    @Test
    public void wrongTest() {
        var menu = new MenuController();

        OutputTestExit output = new OutputTestExit(menu);
        InputTest input = new InputTest('x');

        var testMenu = new TestMenu(menu, output, input);


        menu.Init(testMenu, output, input);
        menu.Loop();

        assertEquals(output.getString(), "Wrong command.");
    }

    private class TestMenu
    {
        private Input input;

        private Output output;

        private Menu menu;

        public TestMenu(Menu menu, Output output, Input input)
        {
            this.input = input;
            this.output = output;
            this.menu = menu;
        }

        @MenuItem(Name = "Menu", Order = 1, Key = 'm')
        public void ShowMenu()
        {
            menu.ShowMenu();

            menu.Exit();
        }
    }

    @Setter @Getter
    private class OutputTestExit implements Output{

        private String string;

        private Menu menu;

        public OutputTestExit(Menu menu)
        {
            this.menu = menu;
        }

        @Override
        public void print(String str) {
            string = str;

            menu.Exit();
        }

        @Override
        public void printLn(String str) {
            string = str;

            menu.Exit();
        }

        @Override
        public void format(String format, Object... args) {
            string = String.format(format, args);

            menu.Exit();
        }
    }

    @Setter @Getter
    private class OutputTest implements Output{

        private String string;

        @Override
        public void print(String str) {
            string = str;
        }

        @Override
        public void printLn(String str) {
            string = str;
        }

        @Override
        public void format(String format, Object... args) {
            string = String.format(format, args);
        }
    }

    private class InputTest implements Input {

        private Character character;

        public InputTest(Character character)
        {
            this.character = character;
        }

        @Override
        public Character GetChar(String description) {
            return character;
        }

        @Override
        public String GetString(String description) {
            return character.toString();
        }

        @Override
        public int GetInt(String description) {
            return character;
        }
    }
}
