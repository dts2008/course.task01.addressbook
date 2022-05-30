package Controller;

import Common.Interface.Database;
import DTO.CommonInfo;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class DatabaseController<T extends CommonInfo> implements Database<T> {

    private AtomicInteger lastIdentity = new AtomicInteger();

    private HashMap<Integer, T> dataBuffer = new HashMap<>();

    @Override
    public Integer Insert(T item) {
        Integer id = lastIdentity.incrementAndGet();
        item.setId(id);

        item.setUpdated(new Timestamp(new Date().getTime()));

        dataBuffer.put(id, item);
        return id;
    }

    @Override
    public boolean Update(T item) {
        var current = dataBuffer.get(item.getId());

        if (current == null)
            return false;

        item.setUpdated(new Timestamp(new Date().getTime()));
        dataBuffer.put(item.getId(), item);

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
