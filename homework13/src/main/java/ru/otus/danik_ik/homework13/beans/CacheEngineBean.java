package ru.otus.danik_ik.homework13.beans;

import ru.otus.danik_ik.homework11.cache.CacheEngineImpl;
import ru.otus.danik_ik.homework11.cache.CacheHelper;
import ru.otus.danik_ik.homework11.storage.dataSets.UserDataSet;

import java.util.function.BiFunction;

public class CacheEngineBean extends CacheEngineImpl<Long, UserDataSet> {
    public CacheEngineBean(int maxElements, long lifeTimeMs, long idleTimeMs, boolean isEternal) {
        super(maxElements, lifeTimeMs, idleTimeMs, isEternal, CacheHelper.SoftEntryFactory());
    }
}

