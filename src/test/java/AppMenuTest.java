import Common.DatabaseCache;
import Common.Interface.*;
import Controller.InputController;
import Controller.MenuController;
import Controller.OutputController;
import DTO.CityInfo;
import DTO.UserInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AppMenuTest {

    @Mock
    private Input input;
    @Mock
    private Output output;
    @Mock
    private Menu menu;

    private Cache cache;

    private AppMenu appMenu;

    @BeforeEach
    void setUp()
    {
        MockitoAnnotations.openMocks(this);
        cache = new DatabaseCache();
        appMenu = new AppMenu(menu, cache, output, input);
    }

    @Test
    public void showMenuTest()
    {
        appMenu.ShowMenu();
        verify(menu).ShowMenu();
    }

    @Test
    public void showUsersTest()
    {
        appMenu.ShowUsers();

        int count = cache.getInfo(UserInfo.class).Select().size();
        int headers = 1;

        verify(output, times(count + headers)).format(anyString(),any());
    }

    @Test
    public void showCitiesTest()
    {
        appMenu.ShowCities();

        int count = cache.getInfo(CityInfo.class).Select().size();
        int headers = 1;

        verify(output, times(count + headers)).format(anyString(),any());
    }

    @Test
    public void addTest()
    {
        when(input.GetString(anyString())).thenReturn("FIO");
        when(input.GetInt(any())).thenReturn(1);

        appMenu.Add();

        assertNotNull(cache.getInfo(UserInfo.class).Select(4));
    }

    @Test
    public void editTest()
    {
        when(input.GetInt(any())).thenReturn(1).thenReturn(2);
        when(input.GetString(anyString())).thenReturn("FIO");

        appMenu.Edit();

        var user = cache.getInfo(UserInfo.class).Select(1);

        assertEquals(user.getCityId(), 2);
        assertEquals(user.getFIO(), "FIO");
    }

    @Test
    public void removeTest()
    {
       when(input.GetInt(any())).thenReturn(1);
       appMenu.Remove();

       assertNull(cache.getInfo(UserInfo.class).Select(1));
    }

    @Test
    public void exitTest()
    {
        appMenu.Exit();
        verify(menu).Exit();
    }

    private CityInfo createCity(int id, String name)
    {
        var city = new CityInfo();

        city.setId(id);
        city.setName(name);

        return city;
    }
}
