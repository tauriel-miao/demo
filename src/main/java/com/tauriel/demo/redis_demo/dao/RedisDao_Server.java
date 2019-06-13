package com.tauriel.demo.redis_demo.dao;

public interface RedisDao_Server extends RedisDao{


    boolean flushDB();

    long dbSize();


}
