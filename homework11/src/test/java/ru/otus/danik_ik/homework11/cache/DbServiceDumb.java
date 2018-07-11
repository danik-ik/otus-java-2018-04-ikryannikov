package ru.otus.danik_ik.homework11.cache;

import ru.otus.danik_ik.homework11.storage.DBService;
import ru.otus.danik_ik.homework11.storage.dataSets.UserDataSet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DbServiceDumb implements DBService {
    private final Map<Long, UserDataSet> map = new HashMap<>();
    private int readCount;
    {
        for (long i = 0; i < 10; i++) {
            UserDataSet dataSet = new UserDataSet();
            dataSet.setID(i);
            dataSet.setName("Name" + i);
            map.put(i, dataSet);
        }
    }

    @Override
    public void save(UserDataSet dataSet) {
        map.put(dataSet.getID(), dataSet);
    }

    @Override
    public UserDataSet read(long id) {
        readCount++;
        return map.get(id);
    }

    @Override
    public UserDataSet readByName(String name) {
        return null;
    }

    @Override
    public List<UserDataSet> readAll() {
        return map.values()
                .stream()
                .collect(Collectors.toList());
    }

    @Override
    public void close() throws Exception {

    }

    public int getReadCount() {
        return readCount;
    }
}
