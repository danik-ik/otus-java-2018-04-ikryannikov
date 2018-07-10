package ru.otus.danik_ik.homework11.cache;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Created by tully.
 */
public class CacheEngineImpl<K, V> implements CacheEngine<K, V> {
    private static final int TIME_THRESHOLD_MS = 5;

    private final int maxElements;
    private final long lifeTimeMs;
    private final long idleTimeMs;
    private final boolean isEternal;
    private final BiFunction<K, V, CacheEntry<K, V>> cacheEntryFactory;

    private final Map<K, CacheEntry<K, V>> elements = new LinkedHashMap<>();
    private final Timer timer = new Timer();

    private int hit = 0;
    private int miss = 0;

    CacheEngineImpl(int maxElements, long lifeTimeMs, long idleTimeMs, boolean isEternal,
                    BiFunction<K, V, CacheEntry<K, V>> cacheEntryFactory) {
        this.maxElements = maxElements;
        this.lifeTimeMs = lifeTimeMs > 0 ? lifeTimeMs : 0;
        this.idleTimeMs = idleTimeMs > 0 ? idleTimeMs : 0;
        this.isEternal = lifeTimeMs == 0 && idleTimeMs == 0 || isEternal;
        this.cacheEntryFactory = cacheEntryFactory;
    }

    @Override
    public void put(K key, V value) {
        if (elements.size() == maxElements) {
            K firstKey = elements.keySet().iterator().next();
            elements.remove(firstKey);
        }

        elements.put(key, cacheEntryFactory.apply(key, value));

        if (!isEternal) {
            if (lifeTimeMs != 0) {
                TimerTask lifeTimerTask = getTimerTask(key, lifeElement -> lifeElement.getCreationTime() + lifeTimeMs);
                timer.schedule(lifeTimerTask, lifeTimeMs);
            }
            if (idleTimeMs != 0) {
                TimerTask idleTimerTask = getTimerTask(key, idleElement -> idleElement.getLastAccessTime() + idleTimeMs);
                timer.schedule(idleTimerTask, idleTimeMs, idleTimeMs);
            }
        }
    }

    @Override
    public V get(K key) {
        CacheEntry<K, V> element = elements.get(key);
        if (element != null) {
            hit++;
            element.setAccessed();
            return element.getValue();
        } else {
            miss++;
            return null;
        }
    }

    @Override
    public V getOrCalculate(K key, Function<K, V> externalGetter) {
        V result = get(key);
        if (result == null) {
            result = externalGetter.apply(key);
            if (result != null) put(key, result);
        }
        return result;
    }

    public int getHitCount() {
        return hit;
    }

    public int getMissCount() {
        return miss;
    }

    @Override
    public void dispose() {
        timer.cancel();
    }

    private TimerTask getTimerTask(final K key, Function<CacheEntry<K, V>, Long> timeFunction) {
        return new TimerTask() {
            @Override
            public void run() {
                CacheEntry<K, V> element = elements.get(key);
                if (element == null || isT1BeforeT2(timeFunction.apply(element), System.currentTimeMillis())) {
                    elements.remove(key);
                    this.cancel();
                }
            }
        };
    }


    private boolean isT1BeforeT2(long t1, long t2) {
        return t1 < t2 + TIME_THRESHOLD_MS;
    }
}
