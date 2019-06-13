package com.tauriel.demo.redis_demo.pub_sub.handler.message_handler.impl;

import com.tauriel.demo.redis_demo.pub_sub.handler.message_handler.MessageHandler;

import java.io.Serializable;
import java.util.Map;

public class MessageHandlerImpl implements MessageHandler{

    @Override
    public void handleMessage(String message) {
        System.out.println("message1 (String): " + message);
    }

    @Override
    public void handleMessage(Map message) {
        System.out.println("message2 (Map) key : " + message.keySet() + " , value : " + message.values());
    }

    @Override
    public void handleMessage(byte[] message) {
        System.out.println("message3 (byte[]) : " + message);
    }

    @Override
    public void handleMessage(Serializable message) {
        System.out.println("message4 (Serializable) : " + message);
    }

    @Override
    public void handleMessage(Serializable message, String channel) {
        System.out.println("message5 (Serializable, String): " + message + " , channel :" + channel);
    }
}
