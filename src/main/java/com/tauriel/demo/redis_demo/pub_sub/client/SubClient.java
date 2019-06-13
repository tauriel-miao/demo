package com.tauriel.demo.redis_demo.pub_sub.client;

import com.tauriel.demo.redis_demo.pub_sub.handler.listener.RedisMessageListener;
import com.tauriel.demo.redis_demo.pub_sub.handler.listener_topic_handler.SubListenerTopicHandler;
import com.tauriel.demo.redis_demo.pub_sub.sub.SubHandler;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;

public class SubClient extends SubListenerTopicHandler {

    private RedisMessageListener handler;
    private MessageListenerAdapter messageListenerAdapter;

    private RedisMessageListenerContainer redisMessageListenerContainer;

    public SubClient(String sessionId, String channel, WarnEnum type, RedisMessageListenerContainer redisMessageListenerContainer) {
        handler = new SubHandler(sessionId, type);//PSubHandler.getInstance(sessionId);
        messageListenerAdapter = new MessageListenerAdapter(handler);
        messageListenerAdapter.afterPropertiesSet();
        this.redisMessageListenerContainer = redisMessageListenerContainer;
    }

    public SubClient(String host, int port, String clientId, SubHandler handler) {
        this.handler = handler;
    }

    // 这个会被阻塞住
    public void sub(String channel) {
        super.sub_channel(redisMessageListenerContainer, messageListenerAdapter, channel);
    }

    public void sub_channel(String channel) {
        super.sub_channel(redisMessageListenerContainer, messageListenerAdapter, channel);
    }

    public void sub_pattern (String channel) {
        super.sub_pattern(redisMessageListenerContainer, messageListenerAdapter, channel);
    }

    public void unsubscribe_channel(String channel) {
        super.unsubscribe_channel(redisMessageListenerContainer, channel);
    }

    public void unsubscribe_pattern(String channel) {
        super.unsubscribe_pattern(redisMessageListenerContainer, channel);
    }

    public enum WarnEnum {
        forward, receive;
    }

}
