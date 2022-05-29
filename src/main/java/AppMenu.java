import Common.DatabaseCache;
import Common.Interface.*;
import DTO.CityInfo;
import DTO.UserInfo;

public class AppMenu {

    private Menu menu;

    private Output output;

    private Input input;

    private Cache cache;

    public AppMenu(Menu menu, Cache cache, Output output, Input input)
    {
        this.menu = menu;
        this.output = output;
        this.input = input;
        this.cache = cache;
    }

    @MenuItem(Name = "Menu", Order = 1, Key = 'm')
    public void ShowMenu()
    {
        menu.ShowMenu();
    }

    @MenuItem(Name = "Show Users", Order = 2, Key = 's')
    public void ShowUsers()
    {

        var users = cache.getInfo(UserInfo.class);

        final int tableSize = 51;

        ShowSeparator(tableSize);
        output.format("| %10s | %20s | %10s |\r\n", "#", "FIO", "City");
        ShowSeparator(tableSize);

        var cities = cache.getInfo(CityInfo.class);
        for (var info: users.Select())
        {
            String cityName = "";

            var city = cities.Select(info.getCityId());
            if (city != null)
                cityName = city.getName();

            output.format("| %10d | %20s | %10s |\r\n",info.getId(),info.getFIO(), cityName);
        }

        ShowSeparator(tableSize);
    }

    @MenuItem(Name = "Show Cities", Key = 'c')
    public void ShowCities()
    {
        var cities = cache.getInfo(CityInfo.class);

        final int tableSize = 38;

        ShowSeparator(tableSize);
        output.format("| %10s | %20s |\r\n","#","Name");
        ShowSeparator(tableSize);

        for (var info: cities.Select())
        {
            output.format("| %10d | %20s |\r\n",info.getId(),info.getName());
        }

        ShowSeparator(tableSize);
    }

    @MenuItem(Name = "Add User", Key = 'a')
    public void Add()
    {
        var user = new UserInfo();

        user.setFIO(input.GetString("FIO: "));

        int cityId = GetCity("City: ");
        if (cityId == DatabaseCache.nulId)
            return;

        user.setCityId(cityId);

        int id = cache.getInfo(UserInfo.class).Insert(user);

        output.format("Added id: #%d\r\n", id);
    }

    @MenuItem(Name = "Edit User", Key = 'e')
    public void Edit()
    {
        int id = input.GetInt("Input user Id: ");

        var user = cache.getInfo(UserInfo.class).Select(id);

        if (user == null)
        {
            output.format("User #%d is wrong \r\n", id);
            return;
        }

        String cityPattern;

        var city = cache.getInfo(CityInfo.class).Select(user.getCityId());

        if (city != null)
            cityPattern = String.format("City (%s #%d): ", city.getName(), user.getCityId());
        else
            cityPattern = String.format("City (#%d): ", user.getCityId());

        user.setFIO(input.GetString(String.format("FIO (%s): ", user.getFIO())));

        int cityId = GetCity(cityPattern);
        if (cityId == DatabaseCache.nulId)
            return;

        user.setCityId(cityId);

        if (cache.getInfo(UserInfo.class).Update(user))
            output.format("Updated User #%d.\r\n", user.getId());
        else
            output.format("Error to update User #%d.\r\n", user.getId());
    }

    @MenuItem(Name = "Remove User", Key = 'r')
    public void Remove()
    {
        int id = input.GetInt("Input user Id: ");

        var user = cache.getInfo(UserInfo.class).Select(id);

        if (user == null) {
            output.format("User #%d is wrong.\r\n", id);
            return;
        }

        if (cache.getInfo(UserInfo.class).Delete(id)) {
            output.format("User #%d was deleted.\r\n", id);
        }
        else {
            output.format("Can't delete User #%d.\r\n", id);
        }
    }

    @MenuItem(Name = "Exit", Key = 'q')
    public void Exit()
    {
        menu.Exit();
    }

    private void ShowSeparator(int size)
    {
        for (int i = 0; i < size; ++i)
            output.print("=");

        output.printLn("");
    }

    private int GetCity(String description)
    {
        var cities = cache.getInfo(CityInfo.class);
        while (true) {
            int cityId = input.GetInt(description);

            var city = cities.Select(cityId);
            if (city == null) {
                output.format("City #%d is wrong.\r\n", cityId);

                String again = input.GetString("Do you want to try again? (y/n): ");
                if (!again.equals("y"))
                    return DatabaseCache.nulId;
            }
            else
                return cityId;
        }
    }
}
