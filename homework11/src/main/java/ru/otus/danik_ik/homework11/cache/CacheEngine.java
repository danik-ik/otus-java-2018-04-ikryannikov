package ru.otus.danik_ik.homework11.cache;

import java.util.function.Function;

/**
 * Created by danik_ik
 * ВНИМАНИЕ!!! Поскольку предусмотрена прозрачная работа с Soft-ссылками, значения null не могут
 * быть эффективно кэшированы, поэтому они вообще не помещаются в кэш. Как правило, получение из
 * кэша значения null обозначает устаревшую ссылку и провоцирует обращение к внешнему хранилищу.
 * 
 * При необходимости сохранения в кэше информации об отсутствии объекта во внешнем хранилище 
 * можно использовать null object 
 */
public interface CacheEngine<K, V> {

    void put(K key, V value);

    V get(K key);

    V getOrCalculate(K key, Function<K,V> externalGetter);

    int getHitCount();

    int getMissCount();

    void dispose();
}
