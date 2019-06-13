package com.tauriel.demo.redis_demo.dao.impl;

import com.tauriel.demo.redis_demo.dao.RedisDao_List;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisListCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class RedisDaoListImpl implements RedisDao_List{

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

    /**
     * ==> LRANGE key start stop
     * 返回列表 key 中指定区间内的元素，区间以偏移量 start 和 stop 指定。(包含start和stop)
     *
     * @return
     */
    @Override
    public <K, T> List<T> lRange(K key, long start, long end) {
        List list = redisTemplate.opsForList().range(key, start, end);
        return list;
    }

    /**
     * ==> LTRIM key start stop
     * 对一个列表进行修剪(trim)，就是说，让列表只保留指定区间内的元素，不在指定区间之内的元素都将被删除。
     *
     * @return  是否执行成功
     */
    @Override
    public <K> boolean lTrim(K key, long start, long end){
        try{
            redisTemplate.opsForList().trim(key, start, end);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * ==> LLEN key
     * 返回列表 key 的长度。
     *
     * @return  列表的长度
     */
    @Override
    public <K> Long lLen(K key){
        return redisTemplate.opsForList().size(key);
    }


    /**
     * ==> LPUSH key value [value ...]
     * 将一个或多个值 value 依次插入到列表 key 的表头
     * 如果有多个 value 值，那么各个 value 值按从左到右的顺序依次插入到表头： 比如说，对空列表 mylist 执行命令 LPUSH mylist a b c ，列表的值将是 c b a ，这等同于原子性地执行 LPUSH mylist a 、 LPUSH mylist b 和 LPUSH mylist c 三个命令。
     *
     * @return 列表的长度
     */
    @Override
    public <K, V> Long lPush(K key, V value){
        return redisTemplate.opsForList().leftPush(key, value);
    }

    @Override
    public <K, V> Long lPush(K key, V... values){
        return redisTemplate.opsForList().leftPushAll(key, values);
    }

    /**
     * ==> RPUSH key value [value ...]
     *
     * @return  列表的长度
     */
    @Override
    public <K,V> Long rPush(K key, V value){
        return redisTemplate.opsForList().rightPush(key, value);
    }

    @Override
    public <K,V> Long rPush(K key, V... values){
        return redisTemplate.opsForList().rightPushAll(key, values);
    }

    /**
     * ==> LPUSHX key value
     * 当key存在时，将value插入到列表key的表头
     *
     * @return  列表的长度
     */
    @Override
    public <K,V> Long lPushX(K key, V value){
        return redisTemplate.opsForList().leftPushIfPresent(key, value);
    }

    /**
     * ==> RPUSHX key value
     * 当key存在时，将value插入到列表key的表尾
     *
     * @return
     */
    @Override
    public <K,V> Long rPushX(K key, V value){
        return redisTemplate.opsForList().rightPushIfPresent(key, value);
    }

    /**
     * ==> LSET key index value
     * 将列表 key 下标为 index 的元素的值设置为 value 。
     *
     * @return 是否成功设置
     */
    @Override
    public <K,V> boolean lSet(K key, long index, V value){
        try{
            redisTemplate.opsForList().set(key, index, value);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * ==> LREM key count value
     * count > 0 : 从表头开始向表尾搜索，移除与 value 相等的元素，数量为 count 。
     * count < 0 : 从表尾开始向表头搜索，移除与 value 相等的元素，数量为 count 的绝对值。
     * count = 0 : 移除表中所有与 value 相等的值。
     *
     * @return  被移除元素的数量
     */
    @Override
    public <K> Long lRem(K key, long count, Object value){
        return redisTemplate.opsForList().remove(key, count, value);
    }

    /**
     * ==>  LINDEX key index
     * 返回列表 key 中，下标为 index 的元素。
     *
     * @return  列表中下标为 index 的元素。
     */
    @Override
    public <K,V> V lIndex(K key, long index){
        return (V) redisTemplate.opsForList().index(key, index);
    }

    /**
     * ==> LPOP key
     * 移除并返回列表 key 的头元素。
     *
     * @return  列表的头元素
     */
    @Override
    public <K,V> V lPop(K key){
        return (V) redisTemplate.opsForList().leftPop(key);
    }

    /**
     * ==> BLPOP key [key ...] timeout
     * BLPOP 是列表的阻塞式(blocking)弹出原语。
     * 它是 LPOP 命令的阻塞版本，当给定列表内没有任何元素可供弹出的时候，连接将被 BLPOP 命令阻塞，直到等待超时或发现可弹出元素为止。
     * 当给定多个 key 参数时，按参数 key 的先后顺序依次检查各个列表，弹出第一个非空列表的头元素。
     *
     * @return
     */
    @Override
    public <K,V> V lPop(K key, long timeout, TimeUnit unit){
        return (V) redisTemplate.opsForList().leftPop(key, timeout, unit);
    }

    /**
     * ==> RPOP key
     * 移除并返回列表 key 的尾元素。
     *
     * @return  列表的尾元素。
     */
    @Override
    public <K,V> V rPop(K key){
        return (V) redisTemplate.opsForList().rightPop(key);
    }

    /**
     * ==> BRPOP key [key ...] timeout
     * BRPOP 是列表的阻塞式(blocking)弹出原语。
     * 它是 RPOP 命令的阻塞版本，当给定列表内没有任何元素可供弹出的时候，连接将被 BRPOP 命令阻塞，直到等待超时或发现可弹出元素为止。
     * 当给定多个 key 参数时，按参数 key 的先后顺序依次检查各个列表，弹出第一个非空列表的尾部元素。
     *
     * return: 假如在指定时间内没有任何元素被弹出，则返回一个 nil 和等待时长。
     * 反之，返回一个含有两个元素的列表，第一个元素是被弹出元素所属的 key ，第二个元素是被弹出元素的值。
     *
     * @return  若指定时间内没有任何元素则返回null， 否则返回表尾的第一个元素
     */
    @Override
    public <K,V> V rPop(K key, long timeout, TimeUnit unit){
        return (V) redisTemplate.opsForList().rightPop(key, timeout, unit);
    }

    /**
     * ==> RPOPLPUSH source destination
     * 命令 RPOPLPUSH 在一个原子时间内，执行以下两个动作：
     * 将列表 source 中的最后一个元素(尾元素)弹出，并返回给客户端。
     * 将 source 弹出的元素插入到列表 destination ，作为 destination 列表的的头元素。
     *
     * @return 被弹出的元素。
     */
    @Override
    public <K,V> V rPopLPush(K sourceKey, K destinationKey){
        return (V) redisTemplate.opsForList().rightPopAndLeftPush(sourceKey, destinationKey);
    }

    /**
     *
     * @return
     */
    @Override
    public <K,V> V rPopLPush(K sourceKey, K destinationKey, long timeout, TimeUnit unit){
        return (V) redisTemplate.opsForList().rightPopAndLeftPush(sourceKey, destinationKey, timeout, unit);
    }

    /**
     * LINSERT key BEFORE|AFTER pivot value
     * 将值 value 插入到列表 key 当中，位于值 pivot 之前或之后。
     * 当 pivot 不存在于列表 key 时，不执行任何操作。
     * 当 key 不存在时， key 被视为空列表，不执行任何操作。
     *
     * @return  如果命令执行成功，返回插入操作完成之后，列表的长度。
     * 如果没有找到 pivot ，返回 -1 。
     * 如果 key 不存在或为空列表，返回 0 。
     */
    @Override
    public <K,V> Long lInsert(final K key, final RedisListCommands.Position flag, final V pivot, final V value){
        Long aLong= (Long) redisTemplate.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                Long aLong = connection.lInsert(redisTemplate.getKeySerializer().serialize(key), flag, redisTemplate.getValueSerializer().serialize(pivot), redisTemplate.getValueSerializer().serialize(value));
                return aLong;
            }
        }, true);
        return aLong;
    }

    /**
     * ==>  BLPOP key [key ...] timeout
     *
     * BLPOP 是列表的阻塞式(blocking)弹出原语。
     * 它是 LPOP 命令的阻塞版本，当给定列表内没有任何元素可供弹出的时候，连接将被 BLPOP 命令阻塞，直到等待超时或发现可弹出元素为止。
     * 当给定多个 key 参数时，按参数 key 的先后顺序依次检查各个列表，弹出第一个非空列表的头元素。
     *
     * @return 假如在指定时间内没有任何元素被弹出，则返回一个 nil 和等待时长。
     * 反之，返回一个含有两个元素的列表，第一个元素是被弹出元素所属的 key ，第二个元素是被弹出元素的值。
     */
    @Override
    public <K,V> Map<String, Object> blPop(int timeout, K... keys){
        final byte[][] bytes = new byte[keys.length][];
        int i = 0;
        for (K key : keys){
            byte[] serialize = redisTemplate.getKeySerializer().serialize(key);
            bytes[i] = serialize;
            i ++;
        }

        List<byte[]> byteList = (List<byte[]>) redisTemplate.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                List<byte[]> byteList = connection.bLPop(timeout, bytes);
                return byteList;
            }
        }, true);

        Map<String, Object> map = new HashMap<String, Object>();
        if(byteList != null && byteList.size() >= 2 && byteList.get(0) != null){
            K key = (K) redisTemplate.getKeySerializer().deserialize(byteList.get(0));
            map.put("key", key);
            V value = (V) redisTemplate.getValueSerializer().deserialize(byteList.get(1));
            map.put("value", value);
        }
        return map;
    }

    /**
     * ==>  BRPOP key [key ...] timeout
     *
     * BRPOP 是列表的阻塞式(blocking)弹出原语。
     * 它是 RPOP 命令的阻塞版本，当给定列表内没有任何元素可供弹出的时候，连接将被 BRPOP 命令阻塞，直到等待超时或发现可弹出元素为止。
     * 当给定多个 key 参数时，按参数 key 的先后顺序依次检查各个列表，弹出第一个非空列表的头元素。
     *
     * @return 假如在指定时间内没有任何元素被弹出，则返回一个 nil 和等待时长。
     * 反之，返回一个含有两个元素的列表，第一个元素是被弹出元素的值，第二个元素是等待时长。
     */
    @Override
    public <K,V> Map<String, Object> brPop(int timeout, K... keys){
        final byte[][] bytes = new byte[keys.length][];
        int i = 0;
        for (K key : keys){
            byte[] serialize = redisTemplate.getKeySerializer().serialize(key);
            bytes[i] = serialize;
            i ++;
        }

        List<byte[]> byteList = (List<byte[]>) redisTemplate.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                List<byte[]> byteList = connection.bRPop(timeout, bytes);
                return byteList;
            }
        }, true);

        Map<String, Object> map = new HashMap<String, Object>();
        if(byteList != null && byteList.size() >= 2){
            K key = (K) redisTemplate.getKeySerializer().deserialize(byteList.get(0));
            map.put("key", key);
            V value = (V) redisTemplate.getValueSerializer().deserialize(byteList.get(1));
            map.put("value", value);
        }
        return map;
    }

    /**
     * ==>  BRPOPLPUSH source destination timeout
     *
     * BRPOPLPUSH 是 RPOPLPUSH 的阻塞版本，当给定列表 source 不为空时， BRPOPLPUSH 的表现和 RPOPLPUSH 一样。
     * 当列表 source 为空时， BRPOPLPUSH 命令将阻塞连接，直到等待超时，或有另一个客户端对 source 执行 LPUSH 或 RPUSH 命令为止。
     * 超时参数 timeout 接受一个以秒为单位的数字作为值。超时参数设为 0 表示阻塞时间可以无限期延长(block indefinitely) 。
     *
     * @return 假如在指定时间内没有任何元素被弹出，则返回一个 nil 和等待时长。
     * 反之，返回一个含有两个元素的列表，第一个元素是被弹出元素的值，第二个元素是等待时长。
     */
    @Override
    public <K,S,V> V bRPopLPush(int timeout, K sourcekey, S destinationKey){
        byte[] bytes = (byte[]) redisTemplate.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] bytes = connection.bRPopLPush(timeout, redisTemplate.getKeySerializer().serialize(sourcekey), redisTemplate.getKeySerializer().serialize(destinationKey));
                return bytes;
            }
        }, true);

        V value = null;
        if(bytes != null){
            value = (V) redisTemplate.getValueSerializer().deserialize(bytes);
        }
        return value;
    }
}
