package ru.otus.danik_ik.homework11.cache;

/**
 * Created by tully.
 */
@SuppressWarnings("WeakerAccess")
public interface CacheEnty<K, V> {

    public K getKey();

    public V getValue();

    public long getCreationTime();

    public long getLastAccessTime();

    public void setAccessed();
}
