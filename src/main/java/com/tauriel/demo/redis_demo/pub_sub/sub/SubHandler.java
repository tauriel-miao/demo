package com.tauriel.demo.redis_demo.pub_sub.sub;

import com.tauriel.demo.redis_demo.pub_sub.client.SubClient;
import com.tauriel.demo.redis_demo.pub_sub.handler.listener.RedisMessageListener;
import org.springframework.data.redis.connection.Message;

public class SubHandler implements RedisMessageListener{

    private String sessionId;
    private SubClient.WarnEnum type;

    public SubHandler(String sessionId, SubClient.WarnEnum type) {
        this.sessionId = sessionId;
        this.type = type;
    }


    @Override
    public void onMessage(Message message, byte[] pattern) {

        try {
            System.out.println("message" + message.toString() + " , pattern : " + pattern);
            switch(type){
                case forward:
                    System.out.println("forward -->  sessionId : " + sessionId + " , message : " + message.getBody());
                    break;
                case receive:
                    System.out.println("receive -->  sessionId : " + sessionId + " , message : " + message.getBody());
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
