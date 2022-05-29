package Common.Interface;

import Controller.DatabaseController;
import DTO.*;
import DTO.UserInfo;

public interface Cache {
    <T extends CommonInfo> DatabaseController<T> getInfo(Class<T> clazz);
}
