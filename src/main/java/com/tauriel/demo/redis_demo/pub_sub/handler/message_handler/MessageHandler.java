package com.tauriel.demo.redis_demo.pub_sub.handler.message_handler;

import java.io.Serializable;
import java.util.Map;

public interface MessageHandler {

    void handleMessage(String message);

    void handleMessage(Map message);

    void handleMessage(byte[] message);

    void handleMessage(Serializable message);

    void handleMessage(Serializable message, String channel);
}
