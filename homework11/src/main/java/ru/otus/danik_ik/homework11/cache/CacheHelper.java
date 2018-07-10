package ru.otus.danik_ik.homework11.cache;

import java.util.function.BiFunction;

public class CacheHelper {
    public static <K, V> BiFunction<K, V, CacheEntry<K, V>> SoftEntryFactory() {
        return (k, v) -> new CacheEntrySoft<>(k, v);
    }

    public static <K, V> BiFunction<K, V, CacheEntry<K, V>> StrongEntryFactory() {
        return (k, v) -> new CacheEntrySoft<>(k, v);
    }
}

