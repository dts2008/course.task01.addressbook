package Controller;

import Common.Interface.Database;
import DTO.CommonInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class DatabaseController<T> implements Database<T> {

    private AtomicInteger lastIdentity = new AtomicInteger();

    private HashMap<Integer, T> dataBuffer = new HashMap<>();

    @Override
    public Integer Insert(T item) {
        Integer id = 0;

        if (item instanceof CommonInfo) {
            id = ((CommonInfo) item).getId();
        }

        if (id == 0)
            id = lastIdentity.incrementAndGet();

        dataBuffer.put(id, item);
        return id;
    }

    @Override
    public boolean Update(Integer id, T item) {
        var current = dataBuffer.get(id);

        if (current == null)
            return false;

        dataBuffer.put(id, item);

        return true;
    }

    @Override
    public boolean Delete(Integer id) {
        var current = dataBuffer.get(id);

        if (current == null)
            return false;

        dataBuffer.remove(id);

        return true;
    }

    @Override
    public T Select(Integer id)
    {
        return dataBuffer.get(id);
    }

    @Override
    public List<T> Select()
    {
        return new ArrayList<T>(dataBuffer.values());
    }

}
