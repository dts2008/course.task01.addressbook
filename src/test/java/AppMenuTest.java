import org.example.addressbook.application.Common.DatabaseCache;
import org.example.addressbook.application.DTO.CityInfo;
import org.example.addressbook.application.DTO.UserInfo;
import org.example.addressbook.application.AppMenu;
import org.example.addressbook.application.Common.Interface.Cache;
import org.example.addressbook.application.Common.Interface.Input;
import org.example.addressbook.application.Common.Interface.Menu;
import org.example.addressbook.application.Common.Interface.Output;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.stream.Collectors;

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

    private static ApplicationContext context;

    @BeforeAll
    static void Init()
    {
        context = new ClassPathXmlApplicationContext("context.xml");
    }

    @BeforeEach
    void setUp()
    {
        MockitoAnnotations.openMocks(this);

        cache = context.getBean(Cache.class);
        appMenu = new AppMenu(output, input, cache);
        appMenu.SetMenuController(menu);
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
        appMenu.showUsers();

        int count = cache.getInfo(UserInfo.class).Select().size();
        int headers = 1;

        verify(output, times(count + headers)).format(anyString(),any());
    }

    @Test
    public void searchUsersTest()
    {
        String namePattern = "Ivan";
        when(input.GetString(anyString())).thenReturn("Ivan");
        appMenu.searchUsers();

        var users = cache.getInfo(UserInfo.class);

        var ivans = users.Select().
                stream().filter(n->n.getFIO().indexOf(namePattern) != -1).
                collect(Collectors.toList()).size();

        int headers = 1;

        verify(output, times(ivans + headers)).format(anyString(),any());
    }

    @Test
    public void showCitiesTest()
    {
        appMenu.showCities();

        int count = cache.getInfo(CityInfo.class).Select().size();
        int headers = 1;

        verify(output, times(count + headers)).format(anyString(),any());
    }

    @Test
    public void addTest()
    {
        when(input.GetString(anyString())).thenReturn("FIO").thenReturn("Email").thenReturn("Phones");
        when(input.GetInt(any())).thenReturn(5).thenReturn(1);
        when(input.GetChar(any())).thenReturn('y');

        appMenu.add();

        assertNotNull(cache.getInfo(UserInfo.class).Select(4));
    }

    @Test
    public void editTest()
    {
        when(input.GetInt(any())).thenReturn(1).thenReturn(2);
        when(input.GetString(anyString())).thenReturn("FIO").thenReturn("Email").thenReturn("Phones");;

        appMenu.edit();

        var user = cache.getInfo(UserInfo.class).Select(1);

        assertEquals(user.getCityId(), 2);
        assertEquals(user.getFIO(), "FIO");
    }

    @Test
    public void removeTest()
    {
       when(input.GetInt(any())).thenReturn(3);
       appMenu.remove();

       assertNull(cache.getInfo(UserInfo.class).Select(3));
    }

    @Test
    public void exitTest()
    {
        appMenu.exit();
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
