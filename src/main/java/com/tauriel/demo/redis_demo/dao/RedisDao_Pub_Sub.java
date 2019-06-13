package com.tauriel.demo.redis_demo.dao;

import org.springframework.data.redis.connection.MessageListener;

public interface RedisDao_Pub_Sub extends RedisDao{


    <T> void psubscribe(final MessageListener listener, final T... topics);

    <T> void subscribe(final MessageListener listener, final T... topics);

    <V> Long publish(final String channel, V msg);



}
