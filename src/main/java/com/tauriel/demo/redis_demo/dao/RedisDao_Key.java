package com.tauriel.demo.redis_demo.dao;

import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisServerCommands;
import org.springframework.data.redis.core.query.SortQuery;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public interface RedisDao_Key extends RedisDao{

    /**
     * Key
     */
    <K> boolean delete(final K key);

    <K> boolean delete(final List<K> keys);

    <K> boolean delKeys(final K key);

    <K> boolean expire(final K key, final long timeout, final TimeUnit unit);

    <K> boolean expireAt(final K key, final long timestamp) ;

    Set<String> keys(String pattern);

    <K> boolean exist(final K key);

    <K> byte[] dump(K key);

    <K> boolean restore(K key, byte[] value, long timeout, TimeUnit unit);

    <K> boolean migrate(final K key, final RedisNode node, final int dbIndex, final RedisServerCommands.MigrateOption option, final long timeout);

    <K> boolean move(final K key, int dbIndex);

    <K> boolean persist(final K key);

    <K> Long ttl(final K key);

    <K> K randomKey();

    <K> boolean randomName(K oldKey, K newKey);

    <K> boolean renameNX(K oldKey, K newKey);

    <K> List sort(SortQuery sortQuery);


}
