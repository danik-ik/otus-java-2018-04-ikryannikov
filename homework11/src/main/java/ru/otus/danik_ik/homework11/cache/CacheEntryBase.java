package ru.otus.danik_ik.homework11.cache;


public abstract class CacheEntryBase<K, V> implements CacheEntry<K, V> {
    private final K key;
    private final long creationTime;
    private long lastAccessTime;


    public CacheEntryBase(K key) {
        this.key = key;
        this.creationTime = getCurrentTime();
        this.lastAccessTime = getCurrentTime();
    }

    protected long getCurrentTime() {
        return System.currentTimeMillis();
    }

    @Override
    public K getKey() {
        return key;
    }

    @Override
    public long getCreationTime() {
        return creationTime;
    }

    @Override
    public long getLastAccessTime() {
        return lastAccessTime;
    }

    @Override
    public void setAccessed() {
        lastAccessTime = getCurrentTime();
    }
}
