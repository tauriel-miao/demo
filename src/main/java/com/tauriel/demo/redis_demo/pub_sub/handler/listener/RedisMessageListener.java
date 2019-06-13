package com.tauriel.demo.redis_demo.pub_sub.handler.listener;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

public interface RedisMessageListener extends MessageListener {

    void onMessage(Message message, byte[] pattern) ;

}
