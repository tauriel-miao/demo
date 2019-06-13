package com.tauriel.demo.redis_demo.dao.impl;

import com.tauriel.demo.redis_demo.dao.RedisDao_Hash;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class RedisDaoHashImpl implements RedisDao_Hash {

    @Resource
    public RedisTemplate redisTemplate;


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


    /**
     * ==> HSET key field value
     *
     * @return 是否添加成功
     */
    @Override
    public <K, H, V> boolean hSet(K key, H hashKey, V value) {
        try{
            redisTemplate.opsForHash().put(key, hashKey, value);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * ==> HMSET key field value [field value ...]
     *
     * @return 是否添加成功
     */
    @Override
    public <K, H, V> boolean hMSet(final K key, HashMap<H, V> map){
        try{
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * ==> HSETNX key field value
     *
     * @return  设置成功，返回 1 。如果给定域已经存在且没有操作被执行，返回 0 。
     */
    @Override
    public <K, H, V> boolean hSetNX(final K key, H hashKey, V value) {
        Boolean isAdd = redisTemplate.opsForHash().putIfAbsent(key, hashKey, value);
        return isAdd;
    }

    /**
     * ==> HEXISTS key field
     * 查看哈希表 key 中，给定域 field 是否存在。
     *
     * @return  如果哈希表含有给定域，返回 1 。 如果哈希表不含有给定域，或 key 不存在，返回 0 。
     */
    @Override
    public <K, H> boolean hExists(K key, H hashKey){
        Boolean hasHashKey = redisTemplate.opsForHash().hasKey(key, hashKey);
        return hasHashKey;
    }

    /**
     * ==>  HDEL key field [field ...]
     * 删除哈希表 key 中的一个或多个指定域，不存在的域将被忽略。
     *
     * @return  被成功移除的域的数量，不包括被忽略的域。
     */
    @Override
    public <K, H> Long hDel(K key, H... hashKeys){
        Long deleteNum = redisTemplate.opsForHash().delete(key, hashKeys);
        return deleteNum;
    }

    /**
     * ==> HGET key field
     */
    public <K, H, V> V hGet(K key, H hashKey){
        V value = (V)redisTemplate.opsForHash().get(key, hashKey);
        return value;
    }

    /**
     * ==> HMGET key field [field ...]
     */
    @Override
    public <K, H, V> List<V> hMGet(K key, List<H> hashKeys){
        List list = redisTemplate.opsForHash().multiGet(key, hashKeys);
        return list;
    }

    /**
     * ==> HGETALL key
     * 返回哈希表 key 中，所有的域和值
     *
     * @return  以列表形式返回哈希表的域和域的值。若 key 不存在，返回空列表。
     */
    @Override
    public <K, H, V> Map<H, V> hGetAll(final K key){
        Map entries = redisTemplate.opsForHash().entries(key);
        return  entries;
    }

    /**
     * ==> HKEYS key
     * 返回哈希表 key 中的所有域。
     *
     * @return 一个包含哈希表中所有域的表。 当 key 不存在时，返回一个空表。
     */
    @Override
    public <K> Set<K> hKeys(final K key){
        Set keys = redisTemplate.opsForHash().keys(key);
        return keys;
    }

    /**
     * ==> HLEN key
     */
    @Override
    public <K> Long hLen(final K key){
        Long size = redisTemplate.opsForHash().size(key);
        return size;
    }

    /**
     * ==> HVALS key
     * 返回哈希表 key 中所有域的值。
     */
    @Override
    public <K, H> List<H> hVals(final K key){
        List values = redisTemplate.opsForHash().values(key);
        return values;
    }

    /**
     * ==> HINCRBY key field increment
     * 增量也可以为负数，相当于对给定域进行减法操作。
     */
    @Override
    public <K, H> Long hIncrBy(final K key, final H hashKey, long delta){
        Long increment = redisTemplate.opsForHash().increment(key, hashKey, delta);
        return increment;
    }

    /**
     * HINCRBYFLOAT key field increment
     * 增量也可以为负数，相当于对给定域进行减法操作。
     */
    @Override
    public <K, H> Double hIncrByFloat(final K key, final H hashKey, double delta){
        Double increment = redisTemplate.opsForHash().increment(key, hashKey, delta);
        return increment;
    }




}
