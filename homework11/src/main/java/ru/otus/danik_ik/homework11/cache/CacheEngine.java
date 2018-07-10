package ru.otus.danik_ik.homework11.cache;

import java.util.function.Function;

/**
 * Created by tully.
 */
public interface CacheEngine<K, V> {

    void put(CacheEnty<K, V> element);

    CacheEnty<K, V> get(K key);

    CacheEnty<K, V> getOrCalculate(K key, Function<K,V> externalGetter);

    int getHitCount();

    int getMissCount();

    void dispose();
}
