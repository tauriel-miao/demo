package com.tauriel.demo.redis_demo.dao;

import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface RedisDao_Set extends  RedisDao{

    /**
     * Set
     */

    <K, V> Long sAdd(K key, V... value) ;

    <K,V> Long sRem(K key, V... value);

    <K,V> V sPop(K key);

    <K,V> boolean sMove (K key, V value, K destKey);

    <K> Long sCard(K key);

    <K,V> boolean sIsMember(K key, V value);

    <K,V> Set<V> sInter(K key, K otherKey);

    <K,V> Set<V> sInter(K key, Collection<K> otherKeys);

    <K> Long sInterStore(K key, K otherKey, K destKey);

    <K> Long sInterStore(K key, Collection<K> otherKeys, K destKey);

    <K,V> Set<V> sUnion(K key, K otherKey);

    <K,V> Set<V> sUnion(K key, Collection<K> otherKeys);

    <K> long sUnionStore (K key, K otherKey, K destKey);

    <K> long sUnionStore(K key, Collection<K> otherKeys, K destKey);

    <K,V> Set<V> sDiff(K key, K otherKey);

    <K,V> Set<V> sDiff(K key, Collection<K> otherKeys);

    <K> long sDiffStore (K key, K otherKey, K destKey);

    <K> long sDiffStore(K key, Collection<K> otherKeys, K destKey);

    <K,V> Set<V> sMembers(K key);

    <K,V> V sRandMember(K key);

    <K,V> List<V> sRandMember(K key, long count);

    <K,V> Set<V> distinctRandomMembers(K key, long count);

}
