package ru.otus.danik_ik.homework11.cache;

import java.util.function.Function;

/**
 * Created by tully.
 */
public interface CacheEngine<K, V> {

    void put(K key, V value);

    V get(K key);

    V getOrCalculate(K key, Function<K,V> externalGetter);

    int getHitCount();

    int getMissCount();

    void dispose();
}
