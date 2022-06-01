import Common.DatabaseCache;
import Common.Interface.*;
import Controller.DatabaseController;
import DTO.CityInfo;
import DTO.UserInfo;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

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

    @MenuItem(Name = "Show Users", Order = 2, Key = 'u')
    public void showUsers()
    {

        var users = cache.getInfo(UserInfo.class);
        showUsersTable(users.Select());
    }

    @MenuItem(Name = "Search Users", Order = 2, Key = 's')
    public void searchUsers()
    {
        var users = cache.getInfo(UserInfo.class);

        String name = input.GetString("Name of user: ");

        var result = users.Select().
                stream().filter(n->n.getFIO().indexOf(name) != -1).
                collect(Collectors.toList());

        showUsersTable(result);
    }

    @MenuItem(Name = "Show Cities", Key = 'c')
    public void showCities()
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
    public void add()
    {
        var user = new UserInfo();

        user.setFIO(input.GetString("FIO: "));

        int cityId = getCity("City: ");
        if (cityId == DatabaseCache.nulId)
            return;

        user.setCityId(cityId);

        user.setEmail(input.GetString("Email: "));
        user.setPhone(input.GetString("Phones: "));

        int id = cache.getInfo(UserInfo.class).Insert(user);

        output.format("Added id: #%d\r\n", id);
    }

    @MenuItem(Name = "Edit User", Key = 'e')
    public void edit()
    {
        int id = input.GetInt("Input user Id: ");

        var user = cache.getInfo(UserInfo.class).Select(id);

        if (user == null)
        {
            output.format("User #%d is wrong \r\n", id);
            return;
        }

        user.setFIO(input.GetString(String.format("FIO (%s): ", user.getFIO())));

        String cityPattern;
        var city = cache.getInfo(CityInfo.class).Select(user.getCityId());

        if (city != null)
            cityPattern = String.format("City (%s #%d): ", city.getName(), user.getCityId());
        else
            cityPattern = String.format("City (#%d): ", user.getCityId());

        int cityId = getCity(cityPattern);
        if (cityId == DatabaseCache.nulId)
            return;

        user.setCityId(cityId);

        user.setEmail(input.GetString(String.format("Email (%s): ", user.getEmail())));
        user.setPhone(input.GetString(String.format("Phones (%s): ", user.getPhone())));

        if (cache.getInfo(UserInfo.class).Update(user))
            output.format("Updated User #%d.\r\n", user.getId());
        else
            output.format("Error to update User #%d.\r\n", user.getId());
    }

    @MenuItem(Name = "Remove User", Key = 'r')
    public void remove()
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
    public void exit()
    {
        menu.Exit();
    }

    private void ShowSeparator(int size)
    {
        for (int i = 0; i < size; ++i)
            output.print("=");

        output.printLn("");
    }

    private int getCity(String description)
    {
        var cities = cache.getInfo(CityInfo.class);
        while (true) {
            int cityId = input.GetInt(description);

            var city = cities.Select(cityId);
            if (city == null) {
                output.format("City #%d is wrong.\r\n", cityId);

                Character again = input.GetChar("Do you want to try again? (y/n): ");
                if (again != 'y')
                    return DatabaseCache.nulId;
            }
            else
                return cityId;
        }
    }

    private void showUsersTable(List<UserInfo> users) {

        var cities = cache.getInfo(CityInfo.class);
        final int tableSize = 110;

        ShowSeparator(tableSize);
        output.format("| %4s | %20s | %10s | %20s | %17s | %20s |\r\n", "#", "FIO", "City", "Email", "Phones", "Updated");
        ShowSeparator(tableSize);


        for (var info: users)
        {
            String cityName = "";

            var city = cities.Select(info.getCityId());
            if (city != null)
                cityName = city.getName();

            var dateFormat = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");

            output.format("| %4d | %20s | %10s | %20s | %17s | %20s |\r\n",info.getId(),info.getFIO(), cityName,
                    info.getEmail(), info.getPhone(),dateFormat.format(info.getUpdated()));
        }

        ShowSeparator(tableSize);
    }
}
