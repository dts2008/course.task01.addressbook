import Common.Interface.*;
import Controller.MenuController;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.mockito.stubbing.OngoingStubbing;

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

        var menu = new MenuController();
        TestMenu testMenu = new TestMenu(menu);

        menu.Init(testMenu, output, input);
        menu.Loop();

        assertEquals(testMenu.isExecuted, true);
    }

    @Test
    public void wrongTest() {
        Mockito.when(input.GetChar(any())).thenReturn('x').thenReturn('m');

        var menu = new MenuController();
        var testMenu = new TestMenu(menu);

        menu.Init(testMenu, output, input);
        menu.Loop();

        verify(output).printLn("Wrong command.");
        assertEquals(testMenu.isExecuted, true);
    }

    private class TestMenu
    {
        @Getter
        private boolean isExecuted;

        private Menu menu;

        public TestMenu(Menu menu)
        {
            isExecuted = false;
            this.menu = menu;
        }

        @MenuItem(Name = "Menu", Order = 1, Key = 'm')
        public void ShowMenu()
        {
            menu.ShowMenu();
            menu.Exit();

            isExecuted = true;
        }
    }
}
