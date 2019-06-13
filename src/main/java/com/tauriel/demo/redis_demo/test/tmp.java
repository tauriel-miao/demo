package com.tauriel.demo.redis_demo.test;

import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

/**
 * @Classname tmp
 * @Description TODO
 * @Date 2019/6/12 14:29
 * @Created by Tauriel
 */
public class tmp {

    @Resource
    private RedisTemplate redisTemplate;

    public void test(){
        redisTemplate.opsForValue();
    }
}
