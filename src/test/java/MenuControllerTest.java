import org.example.addressbook.application.Common.Interface.*;
import org.example.addressbook.application.Controller.MenuController;
import lombok.Getter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class MenuControllerTest {

    @Mock
    private Input input;
    @Mock
    private Output output;

    @BeforeEach
    void setUp()
    {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void commandTest()
    {
        boolean isCall = false;
        Mockito.when(input.GetChar(any())).thenReturn('m');

        TestMenu testMenu = new TestMenu();
        var menu = new MenuController(testMenu, output, input);

        menu.Init();
        menu.Loop();

        assertEquals(testMenu.isExecuted, true);
    }

    @Test
    public void wrongTest() {
        Mockito.when(input.GetChar(any())).thenReturn('x').thenReturn('m');

        TestMenu testMenu = new TestMenu();
        var menu = new MenuController(testMenu, output, input);

        menu.Init();
        menu.Loop();

        verify(output).printLn("Wrong command.");
        assertEquals(testMenu.isExecuted, true);
    }

    private class TestMenu implements MenuItems
    {
        @Getter
        private boolean isExecuted;

        private Menu menu;

        public void SetMenuController(Menu menu)
        {
            isExecuted = false;
            this.menu = menu;
        }

//        public TestMenu(Menu menu)
//        {
//            isExecuted = false;
//            this.menu = menu;
//        }

        @MenuItem(Name = "Menu", Order = 1, Key = 'm')
        public void ShowMenu()
        {
            menu.ShowMenu();
            menu.Exit();

            isExecuted = true;
        }
    }
}
