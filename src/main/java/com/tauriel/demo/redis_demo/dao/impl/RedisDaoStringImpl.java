package com.tauriel.demo.redis_demo.dao.impl;

import com.tauriel.demo.redis_demo.dao.RedisDao_String;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class RedisDaoStringImpl implements RedisDao_String{

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
     * ==> SET key value
     *
     * @return  是否添加成功
     */
    @Override
    public <K, V> boolean set(K key, V value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * ==> SET key value [EX seconds] [PX milliseconds]
     * ==> PSETEX key milliseconds value
     * ==> SETEX key seconds value
     * PX 和 EX 同时出现时，后面的选项会覆盖前面的选项
     * 例如：
     * SET key "value" EX 1000 PX 5000000  -> 按照PX设置
     * SET another-key "value" PX 5000000 EX 1000 -> 按照EX设置
     *
     * @return 是否添加成功
     */
    @Override
    public <K, V> boolean setEx(final K key, final V value, final long timeout, final TimeUnit unit){
        try{
            redisTemplate.opsForValue().set(key, value, timeout, unit);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * ==> SETRANGE key offset value
     * 用 value 参数覆写(overwrite)给定 key 所储存的字符串值，从偏移量 offset 开始。
     * 不存在的 key 当作空白字符串处理。 空白处被"\x00"填充
     *
     * @return  是否添加成功
     */
    @Override
    public <K, V> boolean setRange(K key, V value, long offset){
        try{
            redisTemplate.opsForValue().set(key, value, offset);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * SET key value NX ==> SETNNX key value
     * 只有键不存在时才会对键进行设置操作
     *
     * @return 是否存在key
     */
    @Override
    public <K, V> boolean setNx(K key, V value){
        Boolean ifAbsent = redisTemplate.opsForValue().setIfAbsent(key, value);
        return ifAbsent;
    }

    /**
     * ==> MSET key value [key value ...]
     * 原子性操作， 若存在会覆盖
     *
     * @return 是否添加成功 （总是返回ok, MSET操作不可能失败）
     */
    @Override
    public <K, V> boolean mSet(Map<K, V> map) {
        try{
            redisTemplate.opsForValue().multiSet(map);
            return true;
        }catch(Exception e){
            e.printStackTrace();
        }
       return false;
    }

    /**
     * ==> MSETNX key value [key value ...]
     * 同时设置一个或多个 key-value 对，当且仅当所有给定 key 都不存在。
     * 即使只有一个给定 key 已存在， MSETNX 也会拒绝执行所有给定 key 的设置操作。
     * MSETNX 是原子性的
     *
     * @return 所有的key都成功设置返回1，若至少有一个key已存在则返回0
     */
    @Override
    public <K, V> boolean mSetNx(Map<K, V> map){
        Boolean ifAbsent = redisTemplate.opsForValue().multiSetIfAbsent(map);
        return ifAbsent;
    }

    /**
     * ==> GET key
     *
     * @return 不存在返回null， 存在返回key的值
     */
    @Override
    public <K, V> V get(K key) {
        try{
            V value = (V) redisTemplate.opsForValue().get(key);
            return value;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * ==> GETSET key value
     * 将给定 key 的值设为 value ，并返回 key 的旧值(old value)。
     *
     * @return  key的旧值,若key不存在返回null
     */
    @Override
    public <K, V> V getSet(K key, V value){
        V value_old = (V) redisTemplate.opsForValue().getAndSet(key, value);
        return value_old;
    }

    /**
     * ==> MGET key [key ...]
     *
     * @return 一个包含所有给定key的值的列表
     */
    @Override
    public <K, V> List<V> mGet(List<K> keyList) {
        try{
            List list = redisTemplate.opsForValue().multiGet(keyList);
            return list;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * ==> INCR key
     * 将 key 所储存的值(必须是数字，否则报错)加上增量 1. 若key不存在会先被初始化为0
     *
     * @return  加上increment后的key的值
     */
    @Override
    public <K> Long incr(K key){
        Long increment = redisTemplate.opsForValue().increment(key, 1);
        return increment;
    }

    /**
     * ==> INCRBY key increment  ==> 若increment=1时，等同于 INCR key
     * 将 key 所储存的值(必须是数字，否则报错)加上增量 increment. 若key不存在会先被初始化为0
     *
     * @return  加上increment后的key的值
     */
    @Override
    public <K> Long incrBy(K key, Long delta){
        Long increment = redisTemplate.opsForValue().increment(key, delta);
        return increment;
    }

    /**
     * ==> INCRBYFLOAT key increment
     *
     * @return 加上increment后的key的值
     */
    @Override
    public <K> Double incrByFloat(K key, Double delta){
        Double increment = redisTemplate.opsForValue().increment(key, delta);
        return increment;
    }

    /**
     * ==> DECR key
     * 将 key 所储存的值减去 1
     *
     * @return
     */
    @Override
    public <K> Long decr(final K key){
        Long value = (Long) redisTemplate.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                Long value = connection.decr(redisTemplate.getKeySerializer().serialize(key));               return value;
            }
        }, true);
        return value;
    }

    /**
     * ==> DECRBY key decrement  ==> 若decrement=1 相当于 DECR key
     * 将 key 所储存的值减去减量 decrement 。
     *
     * @return
     */
    @Override
    public <K> Long decrBy(final K key, final Long delta){
        Long value = (Long) redisTemplate.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                Long value = connection.decrBy(redisTemplate.getKeySerializer().serialize(key), delta);
                return value;
            }
        }, true);
        return value;
    }

    /**
     * ==> APPEND key value
     * 如果 key 已经存在并且是一个字符串， APPEND 命令将 value 追加到 key 原来的值的末尾。
     *
     * @return 追加后的长度
     */
    @Override
    public <K> Integer append(K key, String value){
        Integer append = redisTemplate.opsForValue().append(key, value);
        return append;
    }

    /**
     * ==> GETRANGE key start end
     * 返回 key 中字符串值的子字符串，字符串的截取范围由 start 和 end 两个偏移量决定(包括 start 和 end 在内)。
     * -1 表示最后一个字符
     *
     * @return  截取的字符串
     */
    @Override
    public <K> String getRange(K key, long start, long end){
        String value = redisTemplate.opsForValue().get(key, start, end);
        return value;
    }

    /**
     * ==> STRLEN key
     *
     * @return 字符串的长度
     */
    @Override
    public <K> Long strLen(K key){
        Long size = redisTemplate.opsForValue().size(key);
        return size;
    }

    /**
     * ==> SETBIT key offset value
     * 将key存储的value转换为二进制，用 setbit 命令进行 位设置(0/1)
     *
     * @return 替换前的位的值
     */
    @Override
    public <K> boolean setBit(K key, long offset, boolean value){
        Boolean bit = redisTemplate.opsForValue().setBit(key, offset, value);
        return bit;
    }

    /**
     *  ==> GETBIT key offset
     *
     * @return 指定偏移位上的值（key不存在，offset不存在，都返回0）
     */
    @Override
    public <K> boolean getBit(K key, long offset){
        Boolean bit = redisTemplate.opsForValue().getBit(key, offset);
        return bit;
    }

    /**
     * ==> BITCOUNT key [start] [end]
     * 计算给定字符串中，被设置为 1 的比特位的数量。
     *
     * @return  被设置为1的位的数量
     */
    @Override
    public <K> Long bitCount(final K key){
        Long count = (Long) redisTemplate.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                Long count = connection.bitCount(redisTemplate.getKeySerializer().serialize(key));
                return count;
            }
        }, true);
        return count;
    }

    /**
     * ==> BITOP operation destkey key [key ...]
     * 对一个或多个保存二进制位的字符串 key 进行位元操作，并将结果保存到 destkey 上。
     *
     * @return 保存到 destkey 的字符串的长度，和输入 key 中最长的字符串长度相等。
     */
    @Override
    public <K> Long bitOp(final RedisStringCommands.BitOperation operation, final K destKey, final K... keys){
        final byte[][] bytes = new byte[keys.length][];
        int i = 0;
        for (K key : keys){
            byte[] serialize = redisTemplate.getKeySerializer().serialize(key);
            bytes[i] = serialize;
            i ++;
        }
        Long op = (Long) redisTemplate.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                Long op = connection.bitOp(operation, redisTemplate.getKeySerializer().serialize(destKey), bytes);
                return op;
            }
        }, true);
        return op;
    }


}
