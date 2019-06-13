package com.tauriel.demo.redis_demo.dao;

import org.springframework.data.redis.connection.RedisStringCommands;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public interface RedisDao_String extends RedisDao{

    /**
     *  String
     */
    <K, T> boolean set(final K key, final T value);

    <K, T> boolean setEx(final K key, final T value, final long timeout, final TimeUnit unit);

    <K, V> boolean setRange(K key, V value, long offset);

    <K, V> boolean setNx(K key, V value);

    <K, T> boolean mSet(Map<K, T> map);

    <K, V> boolean mSetNx(Map<K, V> map);

    <K, T> T get(final K key);

    <K, V> V getSet(K key, V value);

    <K, T> List<T> mGet(List<K> keyList);

    <K> Long incr (K key);

    <K> Long incrBy(K key, Long delta);

    <K> Double incrByFloat(K key, Double delta);

    <K> Long decr(K key0);

    <K> Long decrBy(final K key, final Long delta);

    <K> Integer append(K key, String value);

    <K> String getRange(K key, long start, long end);

    <K> Long strLen(K key);

    <K> boolean setBit(K key, long offset, boolean value);

    <K> boolean getBit(K key, long offset);

    <K> Long bitCount(final K key);

    <K> Long bitOp(final RedisStringCommands.BitOperation operation, final K destKey, final K... keys);

}
