package ru.otus.danik_ik.homework11.cache;

import java.lang.ref.SoftReference;

public class CacheEntrySoft<K, V> extends CacheEntryBase<K, V>{
    private final SoftReference<V> value;

    public CacheEntrySoft(K key, V value) {
        super(key);
        this.value = new SoftReference<>(value);
    }

    @Override
    public V getValue() {
        return value.get();
    }
}
