package ru.otus.danik_ik.homework11.cachedstorage;

import ru.otus.danik_ik.homework11.cache.CacheEngine;
import ru.otus.danik_ik.homework11.cache.CacheHelper;
import ru.otus.danik_ik.homework11.storage.DBService;
import ru.otus.danik_ik.homework11.storage.dataSets.UserDataSet;

import java.util.List;

public class DbServiceCached implements DBService {
    private final DBService decoratedService;
    private final CacheEngine<Long, UserDataSet> cache;

    public DbServiceCached(DBService decoratedService, int cacheSize) {
        this.decoratedService = decoratedService;
        this.cache = CacheHelper.getSoftCache(cacheSize);
    }

    @Override
    public void save(UserDataSet dataSet) {
        long id = dataSet.getID();
        if (id > 0)
            cache.put(dataSet.getID(), dataSet);
        decoratedService.save(dataSet);
    }

    @Override
    public UserDataSet read(long id) {
        return cache.getOrCalculate(id, id_ -> decoratedService.read(id_));
    }

    @Override
    public UserDataSet readByName(String name) {
        UserDataSet result = decoratedService.readByName(name);
        cache.put(result.getID(), result);
        return result;
    }

    @Override
    public List<UserDataSet> readAll() {
        List<UserDataSet> result = decoratedService.readAll();
        result.forEach(it -> cache.put(it.getID(), it));
        return result;
    }

    @Override
    public void close() throws Exception {
        cache.dispose();
        decoratedService.close();
    }
}
