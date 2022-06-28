package org.example.addressbook.application.Common.Interface;

import java.util.List;

public interface Database<T> {
    Integer Insert(T item);

    boolean Update(T item);

    boolean Delete(Integer id);

    T Select(Integer id);

    List<T> Select();
}

