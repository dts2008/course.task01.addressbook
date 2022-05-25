package Common;

import Controller.DatabaseController;
import DTO.CityInfo;
import DTO.UserInfo;

public final class DatabaseCache {

    public static final int nulId = -1;

    private DatabaseController<UserInfo> users = new DatabaseController<UserInfo>();

    private DatabaseController<CityInfo> cities = new DatabaseController<CityInfo>();

    private static DatabaseCache INSTANCE;

    private DatabaseCache() {
        DefaultDate();
    }

    public static DatabaseCache getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new DatabaseCache();
        }

        return INSTANCE;
    }

    public DatabaseController<UserInfo> getUsers()
    {
        return users;
    }

    public DatabaseController<CityInfo> getCities()
    {
        return cities;
    }

    private void DefaultDate()
    {
        AddCities();
        AddUsers();
    }

    private void AddCities()
    {
        var kyiv = new CityInfo();
        kyiv.setId(1);
        kyiv.setName("Kyiv");

        var lviv = new CityInfo();
        lviv.setId(2);
        lviv.setName("Lviv");

        var kharkiv = new CityInfo();
        kharkiv.setId(3);
        kharkiv.setName("Kharkiv");

        cities.Insert(kyiv);
        cities.Insert(lviv);
        cities.Insert(kharkiv);
    }

    private void AddUsers()
    {
        var user1 = new UserInfo();
        user1.setId(1);
        user1.setCityId(1);
        user1.setFIO("Ivan Ivanovich");

        var user2 = new UserInfo();
        user2.setId(2);
        user2.setCityId(2);
        user2.setFIO("Petr Petrovich");

        var user3 = new UserInfo();
        user3.setId(3);
        user3.setCityId(3);
        user3.setFIO("Michael Kirilov");

        users.Insert(user1);
        users.Insert(user2);
        users.Insert(user3);
    }
}
