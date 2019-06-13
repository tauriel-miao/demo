package com.tauriel.demo.redis_demo.dao;

import org.springframework.data.redis.connection.RedisListCommands;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public interface RedisDao_List extends RedisDao{

    /**
     *  List
     */

    <K, T> List<T> lRange(K key, long start, long end) ;

    <K> boolean lTrim(K key, long start, long end);

    <K> Long lLen(K key);

    <K, V> Long lPush(K key, V value);

    <K, V> Long lPush(K key, V... values);

    <K,V> Long rPush(K key, V value);

    <K,V> Long rPush(K key, V... values);

    <K,V> Long lPushX(K key, V value);

    <K,V> Long rPushX(K key, V value);

    <K,V> boolean lSet(K key, long index, V value);

    <K> Long lRem(K key, long count, Object value);

    <K,V> V lIndex(K key, long index);

    <K,V> V lPop(K key);

    <K,V> V lPop(K key, long timeout, TimeUnit unit);

    <K,V> V rPop(K key);

    <K,V> V rPop(K key, long timeout, TimeUnit unit);

    <K,V> V rPopLPush(K sourceKey, K destinationKey);

    <K,V> V rPopLPush(K sourceKey, K destinationKey, long timeout, TimeUnit unit);

    <K,V> Long lInsert(final K key, final RedisListCommands.Position flag, final V pivot, final V value);

    <K,V> Map<String, Object> blPop(int timeout, K... keys);

    <K,V> Map<String, Object> brPop(int timeout, K... keys);

    <K,S,V> V bRPopLPush(int timeout, K sourcekey, S destinationKey);
}
