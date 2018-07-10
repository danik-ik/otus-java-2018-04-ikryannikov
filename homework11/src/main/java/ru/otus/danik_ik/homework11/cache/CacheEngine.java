package ru.otus.danik_ik.homework11.cache;

/**
 * Created by tully.
 */
public interface CacheEngine<K, V> {

    void put(CacheEnty<K, V> element);

    CacheEnty<K, V> get(K key);

    int getHitCount();

    int getMissCount();

    void dispose();
}
