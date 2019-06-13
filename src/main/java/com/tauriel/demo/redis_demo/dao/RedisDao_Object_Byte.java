package com.tauriel.demo.redis_demo.dao;

import java.util.List;

public interface RedisDao_Object_Byte extends  RedisDao{

    /**
     * Object
     */
    <K, T> boolean addObject(final K key, final T obj);

    <K, T> void addObject_expire(final K key, final long timeout, final T obj);

    <K, T> T get(final K key, final Class clazz);

    public <K, T> T get(final List<K> key, final Class clazz);


    /**
     * byte[]
     */
    <K> void addBytes(final K key, final byte[] object);

    <K> void addBytes(final K key, final long timeout, final byte[] object);

    <K> byte[] getByte(final K key);



}
