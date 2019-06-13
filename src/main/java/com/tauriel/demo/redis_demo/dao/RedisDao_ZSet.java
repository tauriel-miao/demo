package com.tauriel.demo.redis_demo.dao;

import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.data.redis.core.ZSetOperations;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface RedisDao_ZSet extends RedisDao{


    <K, V> boolean zAdd(K key, V member, double score);

    <K> long zAdd(K key, Set<ZSetOperations.TypedTuple<Serializable>> tuples);

    <K, T> long zAdd(K key, Map<Double, T> map);

    <K,T> Set<T> zRange(K key, long start, long end);

    <K> Set<RedisZSetCommands.Tuple> zRangeByScore(K key, long start, long end);

    <K> Set<RedisZSetCommands.Tuple> zRangeByScore(K key, long start, long end, double mix, double max);

    <K> Set<RedisZSetCommands.Tuple> zRevRange(K key, long start, long end);

    <K> Set<RedisZSetCommands.Tuple> zRangeByScore(K key, double start, double end);

    Set<RedisZSetCommands.Tuple> zRangeByScore(String key, long start, long end, double mix, double max);

    <K> Long zCard(K key);

    <K> long zCount(K key, double mix, double max);

    <K,V> Double zIncrBy(K key, V value, double delta);

    <K,V> Long zRank(K key, V value);

    <K,V> Long zRevRank(K key, V value);

    <K,V> Long zRem(K key, V value);

    <K,V> Long zRem(K key, V... value);

    <K,V> Long zRemRangeByRank(K key, long start, long end);

    <K,V> Long zRemRangeByScore(K key, double start, double end);

    <K,V> Double zScore(K key, V value);

    <K,S,T> Long zUnionStore(K key, T otherKey, S destKey);

    <K,V> Long zUnionStore(K key, Collection<K> otherKeys, K destKey);

    <K,V> Long zInterStore(K key, K otherKey, K destKey);

    <K,V> Long zInterStore(K key, Collection<K> otherKeys, K destKey);
}
