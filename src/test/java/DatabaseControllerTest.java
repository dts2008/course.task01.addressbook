import org.example.addressbook.application.Common.Interface.Database;
import org.example.addressbook.application.Controller.DatabaseController;
import org.example.addressbook.application.DTO.CityInfo;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.example.addressbook.application.DTO.UserInfo;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

public class DatabaseControllerTest {

    private final CityInfo city1 = createCity("Kyiv");

    private final CityInfo city2 = createCity("New-York");

    private final CityInfo city3 = createCity("Paris");

    private final UserInfo user = createUser("Piter Pen", 1);

    @Test
    public void insertTest() {
        Database database = new DatabaseController<CityInfo>();

        int id = database.Insert(city1);

        assertEquals(1, city1.getId());
    }

    @Test
    public void updateTest() {
    }

    @Test
    public void deleteTest() {
        Database database = new DatabaseController<CityInfo>();

        database.Insert(city1);
        assertEquals(true, database.Delete(city1.getId()));
    }

    @Test
    public void selectIdTest(){
        Database database = new DatabaseController<CityInfo>();

        assertEquals(1, database.Insert(city1));
        assertEquals(2, database.Insert(city2));

        assertNotNull(database.Select(2));
    }

    @Test
    public void selectTest()
    {
        Database cities = new DatabaseController<CityInfo>();
        Database users = new DatabaseController<UserInfo>();

        assertEquals(1, cities.Insert(city1));
        assertEquals(2, cities.Insert(city2));
        assertEquals(3, cities.Insert(city3));

        assertEquals(1, users.Insert(user));

        List<CityInfo> listCities = cities.Select();
        assertNotNull(cities.Select());

        var citiesId = listCities.stream().map(cityInfo -> cityInfo.getId()).collect(Collectors.toList());

        assertArrayEquals(citiesId.toArray(), new Integer[]{1,2,3});
    }

    private CityInfo createCity(String name)
    {
        var city = new CityInfo();

        city.setName(name);

        return city;
    }

    private UserInfo createUser(String fio, int cityId)
    {
        var user = new UserInfo();

        user.setFIO(fio);
        user.setCityId(cityId);

        return user;
    }
}
