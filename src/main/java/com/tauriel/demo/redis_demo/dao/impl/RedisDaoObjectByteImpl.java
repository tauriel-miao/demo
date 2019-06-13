package com.tauriel.demo.redis_demo.dao.impl;

import com.tauriel.demo.redis_demo.dao.RedisDao_Object_Byte;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RedisDaoObjectByteImpl implements RedisDao_Object_Byte{

    /**
     * redisTemplate.opsForValue();//操作字符串
     * redisTemplate.opsForHash();//操作hash
     * redisTemplate.opsForList();//操作list
     * redisTemplate.opsForSet();//操作set
     * redisTemplate.opsForZSet();//操作有序set
     */

    /**
     * StringRedisTemplate与RedisTemplate
     * 两者的关系是StringRedisTemplate继承RedisTemplate。
     * 两者的数据是不共通的；也就是说StringRedisTemplate只能管理StringRedisTemplate里面的数据，RedisTemplate只能管理RedisTemplate中的数据。
     * SDR默认采用的序列化策略有两种，一种是String的序列化策略，一种是JDK的序列化策略。
     * StringRedisTemplate默认采用的是String的序列化策略，保存的key和value都是采用此策略序列化保存的。
     * RedisTemplate默认采用的是JDK的序列化策略，保存的key和value都是采用此策略序列化保存的。
     */
    @Resource
    public RedisTemplate redisTemplate;

    @Override
    public <K, T> boolean addObject(K key, T obj) {
        return false;
    }

    @Override
    public <K, T> void addObject_expire(K key, long timeout, T obj) {

    }

    @Override
    public <K, T> T get(K key, Class clazz) {
        return null;
    }

    @Override
    public <K, T> T get(List<K> key, Class clazz) {
        return null;
    }

    @Override
    public <K> void addBytes(K key, byte[] object) {

    }

    @Override
    public <K> void addBytes(K key, long timeout, byte[] object) {

    }

    @Override
    public <K> byte[] getByte(K key) {

        return new byte[0];
    }




}
