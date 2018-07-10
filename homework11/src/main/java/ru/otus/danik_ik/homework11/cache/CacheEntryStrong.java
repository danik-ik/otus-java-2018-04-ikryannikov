package ru.otus.danik_ik.homework11.cache;

/**
 * Created by tully.
 */
@SuppressWarnings("WeakerAccess")
public class CacheEntryStrong<K, V>  implements CacheEnty {
    private final K key;
    private final V value;
    private final long creationTime;
    private long lastAccessTime;


    public CacheEntryStrong(K key, V value) {
        this.key = key;
        this.value = value;
        this.creationTime = getCurrentTime();
        this.lastAccessTime = getCurrentTime();
    }

    protected long getCurrentTime() {
        return System.currentTimeMillis();
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public long getCreationTime() {
        return creationTime;
    }

    public long getLastAccessTime() {
        return lastAccessTime;
    }

    public void setAccessed() {
        lastAccessTime = getCurrentTime();
    }
}
