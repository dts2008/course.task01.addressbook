package Common;

import Common.Interface.Cache;
import Controller.DatabaseController;
import DTO.*;
import java.util.HashMap;
import java.util.Map;

public final class DatabaseCache implements Cache {

    public static final int nulId = -1;

    private DatabaseController<UserInfo> users = new DatabaseController<UserInfo>();

    private DatabaseController<CityInfo> cities = new DatabaseController<CityInfo>();

    private Map<String, DatabaseController<?>> items = new HashMap<>();

    public DatabaseCache() {
        DefaultDate();
    }

    public <T extends CommonInfo> DatabaseController<T> getInfo(Class<T> clazz)
    {
        var item = items.get(clazz.getTypeName());

        return (DatabaseController<T>)item;
    }

    private void DefaultDate()
    {
        AddCities();
        AddUsers();

        items.put(CityInfo.class.getTypeName(), cities);
        items.put(UserInfo.class.getTypeName(), users);
    }

    private void AddCities()
    {
        var kyiv = new CityInfo();
        kyiv.setName("Kyiv");

        var lviv = new CityInfo();
        lviv.setName("Lviv");

        var kharkiv = new CityInfo();
        kharkiv.setName("Kharkiv");

        cities.Insert(kyiv);
        cities.Insert(lviv);
        cities.Insert(kharkiv);
    }

    private void AddUsers()
    {
        var user1 = new UserInfo();
        user1.setCityId(1);
        user1.setFIO("Ivan Ivanovich");

        var user2 = new UserInfo();
        user2.setCityId(2);
        user2.setFIO("Petr Petrovich");

        var user3 = new UserInfo();
        user3.setCityId(3);
        user3.setFIO("Michael Kirilov");

        users.Insert(user1);
        users.Insert(user2);
        users.Insert(user3);
    }
}
