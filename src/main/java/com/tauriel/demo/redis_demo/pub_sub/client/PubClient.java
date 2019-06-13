package com.tauriel.demo.redis_demo.pub_sub.client;

import com.tauriel.demo.redis_demo.dao.RedisDao_Pub_Sub;

public class PubClient {

    public RedisDao_Pub_Sub redisDao;

    private String sessionId;
    private SubClient.WarnEnum type;

    public PubClient(String sessionId , SubClient.WarnEnum type , RedisDao_Pub_Sub redisDao){
        this.sessionId = sessionId;
        this.type = type;
        this.redisDao = redisDao;
    }

    public void pub(String channel,String message ) {
        try {
            redisDao.publish(channel, message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
