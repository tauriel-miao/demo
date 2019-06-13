package com.tauriel.demo.redis_demo.pub_sub.client;

import com.tauriel.demo.redis_demo.pub_sub.handler.listener.RedisMessageListener;
import com.tauriel.demo.redis_demo.pub_sub.handler.listener_topic_handler.SubListenerTopicHandler;
import com.tauriel.demo.redis_demo.pub_sub.sub.SubHandler;
import com.tauriel.demo.redis_demo.pub_sub.sub.SubHandler_2;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import javax.annotation.Resource;

public class SubClient_2 extends SubListenerTopicHandler {

    private RedisMessageListener handler;
    private MessageListenerAdapter messageListenerAdapter;
    private RedisMessageListenerContainer messageListenerContainer;

    public SubClient_2(String sessionId, RedisMessageListenerContainer redisMessageListenerContainer) {
        handler = new SubHandler_2(sessionId);//PSubHandler.getInstance(sessionId);
        messageListenerAdapter = new MessageListenerAdapter(handler);
        messageListenerAdapter.afterPropertiesSet();
        messageListenerContainer = redisMessageListenerContainer;
    }

    public SubClient_2(String host, int port, String clientId, SubHandler handler) {
        this.handler = handler;
    }

    // 这个会被阻塞住
    public void sub(String channel) {
        super.sub_channel(messageListenerContainer, messageListenerAdapter, channel);
    }

    public void sub_channel(String channel) {
        super.sub_channel(messageListenerContainer, messageListenerAdapter, channel);
    }

    public void sub_pattern (String channel) {
        super.sub_pattern(messageListenerContainer, messageListenerAdapter, channel);
    }

    public void unsubscribe_channel(String channel) {
        super.unsubscribe_channel(messageListenerContainer, channel);
    }

    public void unsubscribe_pattern(String channel) {
        super.unsubscribe_pattern(messageListenerContainer, channel);
    }

    public enum WarnEnum {
        forward, receive;
    }

}
