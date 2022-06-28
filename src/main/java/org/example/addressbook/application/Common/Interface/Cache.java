package org.example.addressbook.application.Common.Interface;

import org.example.addressbook.application.Controller.DatabaseController;
import org.example.addressbook.application.DTO.CommonInfo;

public interface Cache {
    <T extends CommonInfo> DatabaseController<T> getInfo(Class<T> clazz);
}
