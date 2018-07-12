package ru.otus.danik_ik.homework11.cache;

import org.junit.Assert;
import org.junit.Test;

public class CacheTest {
    @Test
    public void eternalCacheExample() {
        int size = 6;
        CacheEngine<Integer, String> cache = new CacheEngineImpl<>(size, 0, 0, true,
                CacheHelper.StrongEntryFactory());

        for (int i = 0; i < 10; i++) {
            cache.put(i, "String: " + i);
        }

        for (int i = 0; i < 10; i++) {
            String s = cache.get(i);
            System.out.println("String for " + i + ": " + (s != null ? s : "null"));
        }

        System.out.println("Cache hits: " + cache.getHitCount());
        System.out.println("Cache misses: " + cache.getMissCount());

        cache.dispose();
    }

    @Test
    public void lifeCacheExample() throws InterruptedException {
        int size = 5;
        CacheEngine<Integer, String> cache = new CacheEngineImpl<>(size, 1000, 0, false,
                CacheHelper.SoftEntryFactory());

        for (int i = 0; i < size; i++) {
            cache.put(i, "String: " + i);
        }

        for (int i = 0; i < size; i++) {
            String s = cache.get(i);
            System.out.println("String for " + i + ": " + (s != null ? s : "null"));
        }

        System.out.println("Cache hits: " + cache.getHitCount());
        System.out.println("Cache misses: " + cache.getMissCount());

        Thread.sleep(1000);

        for (int i = 0; i < size; i++) {
            String s = cache.get(i);
            System.out.println("String for " + i + ": " + (s != null ? s : "null"));
        }

        System.out.println("Cache hits: " + cache.getHitCount());
        System.out.println("Cache misses: " + cache.getMissCount());

        cache.dispose();
    }

    @Test
    public void softCacheMemoryOversize() throws InterruptedException {
        final int blockSize = 10_000_000;
        int size = (int) (Runtime.getRuntime().maxMemory() / blockSize * 2); 
        // заполненный полностью кэш должен занять больше реального размера памяти 
        
        CacheEngine<Integer, byte[]> cache = CacheHelper.getSoftCache(size);

        System.out.println("Writing to cache more than real memory size");
        for (int i = 0; i < size; i++) {
            cache.put(i, new byte[blockSize]);
        }

        for (int i = 0; i < size; i++) {
            byte[] it = cache.get(i);
            System.out.println("at index " + i + ": " + (it != null ? "hit" : "miss"));
        }

        Assert.assertTrue(cache.getHitCount() > 0);
        Assert.assertTrue(cache.getMissCount() > 0);

        cache.dispose();
    }
}
