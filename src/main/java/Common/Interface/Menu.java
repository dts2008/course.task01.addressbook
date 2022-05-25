package Common.Interface;

import java.lang.reflect.InvocationTargetException;

public interface Menu {
    void Loop() throws InvocationTargetException, IllegalAccessException;

    void Exit();

    void ShowMenu();
}
