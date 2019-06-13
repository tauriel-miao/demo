package com.tauriel.demo.redis_demo.dao;

import org.springframework.data.redis.core.Cursor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface RedisDao_Hash extends RedisDao{


    /**
     * Hash
     */
    <K, H, V> boolean hSet(final K key, H hashKey, V value);

    <K, H, V> boolean hMSet(final K key, HashMap<H, V> map);

    <K, H, V> boolean hSetNX(final K key, H hashKey, V value);

    <K, H> boolean hExists(K key, H hashKey);

    <K, H> Long hDel(K key, H... hashKeys);

    <K, H, V> V hGet(K key, H hashKey);

    <K, H, V> List<V> hMGet(K key, List<H> hashKeys);

    <K, H, V> Map<H, V> hGetAll(final K key);

    <K> Set<K> hKeys(final K key);

    <K> Long hLen(final K key);

    <K, H> List<H> hVals(final K key);

    <K, H> Long hIncrBy(final K key, final H hashKey, long delta);

    <K, H> Double hIncrByFloat(final K key, final H hashKey, double delta);



}
