package com.tauriel.demo.redis_demo.dao.impl;

import com.tauriel.demo.redis_demo.dao.RedisDao_Pub_Sub;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RedisDaoPubSubImpl implements RedisDao_Pub_Sub{

    @Resource
    private RedisTemplate redisTemplate;

    /**
     * ==> PSUBSCRIBE pattern [pattern ...]
     * 订阅一个或多个符合给定模式的频道。
     *
     * @return 接收到的信息
     */
    @Override
    public <T> void psubscribe(final MessageListener listener, final T... topics){
        final byte[][] bytes = new byte[topics.length][];
        int i = 0;
        for (T key : topics){
            byte[] serialize = redisTemplate.getKeySerializer().serialize(key);
            bytes[i] = serialize;
            i ++;
        }
        redisTemplate.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                connection.pSubscribe(listener, bytes);
                return null;
            }
        }, true);
    }

    /**
     * ==> SUBSCRIBE channel [channel ...]
     */
    @Override
    public <T> void subscribe(final MessageListener listener, final T... topics){
        final byte[][] bytes = new byte[topics.length][];
        int i = 0;
        for (T key : topics){
            byte[] serialize = redisTemplate.getKeySerializer().serialize(key);
            bytes[i] = serialize;
            i ++;
        }
        redisTemplate.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                connection.subscribe(listener, bytes);
                return null;
            }
        }, true);
    }

    /**
     * ==> PUBLISH channel message
     *
     * @return 接收到信息 message 的订阅者数量。
     */
    @Override
    public <V> Long publish(final String channel, final V msg){
       /* Long size = (Long) redisTemplate.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                Long size = connection.publish(redisTemplate.getKeySerializer().serialize(channel), redisTemplate.getValueSerializer().serialize(msg));
                return size;
            }
        }, true);*/
       redisTemplate.convertAndSend(channel, msg);
        return 0L;
    }

}
