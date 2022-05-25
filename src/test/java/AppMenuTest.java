import Common.DatabaseCache;
import Common.Interface.Input;
import Common.Interface.Menu;
import Common.Interface.Output;
import Controller.InputController;
import Controller.OutputController;
import DTO.CityInfo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AppMenuTest {
    @Test
    public void showMenuTest()
    {
        Menu menu;
        Output output;
        Input input;

        var appMenu = new AppMenu(null, null, null);
    }

    @Test
    public void showUsersTest()
    {
        Output output = new OutputController();
        Input input = new InputController(output);

        var appMenu = new AppMenu(null, output, input);

        assertDoesNotThrow(() -> appMenu.ShowMenu());
    }

    @Test
    public void showCitiesTest()
    {
    }

    @Test
    public void addTest()
    {
    }

    @Test
    public void editTest()
    {
    }

    @Test
    public void removeTest()
    {
    }

    @Test
    public void exitTest()
    {
    }

    private CityInfo createCity(int id, String name)
    {
        var city = new CityInfo();

        city.setId(id);
        city.setName(name);

        return city;
    }
}
