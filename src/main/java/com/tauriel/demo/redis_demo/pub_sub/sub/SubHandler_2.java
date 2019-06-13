package com.tauriel.demo.redis_demo.pub_sub.sub;

import com.tauriel.demo.redis_demo.pub_sub.client.SubClient;
import com.tauriel.demo.redis_demo.pub_sub.handler.listener.RedisMessageListener;
import org.springframework.data.redis.connection.Message;

public class SubHandler_2 implements RedisMessageListener{

    private String sessionId;

    public SubHandler_2(String sessionId) {
        this.sessionId = sessionId;
    }


    @Override
    public void onMessage(Message message, byte[] pattern) {

        try {
            System.out.println(" 2222   message" + message.toString() + " , pattern : " + pattern);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
