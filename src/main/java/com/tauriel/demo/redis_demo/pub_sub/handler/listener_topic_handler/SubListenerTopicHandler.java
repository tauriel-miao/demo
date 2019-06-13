package com.tauriel.demo.redis_demo.pub_sub.handler.listener_topic_handler;

import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.util.ByteArrayWrapper;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.lang.reflect.Field;
import java.util.*;

public abstract class SubListenerTopicHandler {

    public abstract void sub(String channel) ;
    public void sub_channel(RedisMessageListenerContainer messageListenerContainer, MessageListenerAdapter messageListenerAdapter, String channel) {
        ArrayList<Topic> topics = new ArrayList<>();
        topics.add(new ChannelTopic(channel));
        messageListenerContainer.addMessageListener(messageListenerAdapter, topics);
        messageListenerContainer.start();
    }
    public void sub_pattern (RedisMessageListenerContainer messageListenerContainer, MessageListenerAdapter messageListenerAdapter, String channel) {
        Set<Topic> topics = new HashSet<>();
        topics.add(new PatternTopic(channel));
        messageListenerContainer.addMessageListener(messageListenerAdapter, topics);
        messageListenerContainer.start();
    }


    public void unsubscribe_channel (RedisMessageListenerContainer messageListenerContainer, String channel) {
        try {
            removeListener4Container(messageListenerContainer, channel);
            removeChannelMapping4Container(messageListenerContainer, channel);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void unsubscribe_pattern (RedisMessageListenerContainer messageListenerContainer, String channel) {
        try {
            Class<? extends RedisMessageListenerContainer> messageListenerContainerClass = messageListenerContainer.getClass();
            removeListener4Container(messageListenerContainer, channel);
            removePatternMapping4Container(messageListenerContainer, channel);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void removeListener4Container(RedisMessageListenerContainer messageListenerContainer, String channel) throws NoSuchFieldException, IllegalAccessException {
        Class<? extends RedisMessageListenerContainer> messageListenerContainerClass = messageListenerContainer.getClass();
        Field listenerTopicsFeild = messageListenerContainerClass.getDeclaredField("listenerTopics");
        listenerTopicsFeild.setAccessible(true);
        //获取属性值
        Map<MessageListener, Set<Topic>> listenerTopicsMap = (Map<MessageListener, Set<Topic>>) listenerTopicsFeild.get(messageListenerContainer);
        for (Map.Entry<MessageListener, Set<Topic>> entry : listenerTopicsMap.entrySet()){
            Set<Topic> topics = entry.getValue();
            for (Topic topic : topics){
                if(topic.getTopic().equals(channel)){
                    topics.remove(topic);
                }
            }
            if(topics.isEmpty()){
                messageListenerContainer.removeMessageListener(entry.getKey());
            }
        }
    }

    public void removeChannelMapping4Container(RedisMessageListenerContainer messageListenerContainer, String channel) throws NoSuchFieldException, IllegalAccessException {

        Class<? extends RedisMessageListenerContainer> messageListenerContainerClass = messageListenerContainer.getClass();
        Field channelMappingField = messageListenerContainerClass.getDeclaredField("channelMapping");
        channelMappingField.setAccessible(true);
        Map<ByteArrayWrapper, Collection<MessageListener>> channelMappingMap = (Map<ByteArrayWrapper, Collection<MessageListener>>) channelMappingField.get(messageListenerContainer);

        Field serializerFeild = messageListenerContainerClass.getDeclaredField("serializer");
        serializerFeild.setAccessible(true);
        RedisSerializer<String> serializer = (RedisSerializer<String>) serializerFeild.get(messageListenerContainer);

        ByteArrayWrapper holder = new ByteArrayWrapper(serializer.serialize(channel));
        if(channelMappingMap.containsKey(holder)){
            channelMappingMap.remove(holder);
        }
    }

    public void removePatternMapping4Container(RedisMessageListenerContainer messageListenerContainer, String channel) throws NoSuchFieldException, IllegalAccessException {

        Class<? extends RedisMessageListenerContainer> messageListenerContainerClass = messageListenerContainer.getClass();
        Field patternMappingField = messageListenerContainerClass.getDeclaredField("patternMapping");
        patternMappingField.setAccessible(true);
        Map<ByteArrayWrapper, Collection<MessageListener>> channelMappingMap = (Map<ByteArrayWrapper, Collection<MessageListener>>) patternMappingField.get(messageListenerContainer);

        Field serializerFeild = messageListenerContainerClass.getDeclaredField("serializer");
        serializerFeild.setAccessible(true);
        RedisSerializer<String> serializer = (RedisSerializer<String>) serializerFeild.get(messageListenerContainer);

        ByteArrayWrapper holder = new ByteArrayWrapper(serializer.serialize(channel));
        if(channelMappingMap.containsKey(holder)){
            channelMappingMap.remove(holder);
        }
    }



}
