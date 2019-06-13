package com.tauriel.demo.redis_demo.test;

import com.tauriel.demo.redis_demo.dao.RedisDao_Pub_Sub;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//@Component
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-context.xml" })
public class Pub_SubTest {

    @Autowired
    public RedisDao_Pub_Sub redisDao;
    @Autowired
    public RedisTemplate redisTemplate;


    @Test
    public void psubscribe() throws InterruptedException {
       redisDao.psubscribe(new MessageListener() {
           @Override
           public void onMessage(Message message, byte[] pattern) {
               System.out.println("psub ------ " + message);
           }
       }, "news*");
       Thread.sleep(Integer.MAX_VALUE);
    }

    @Test
    public void subscribe() throws InterruptedException {
       redisDao.subscribe(new MessageListener() {
           @Override
           public void onMessage(Message message, byte[] pattern) {
               System.out.println("sub ~~~ " + message);
           }
       }, "news.1", "news.3");
       Thread.sleep(Integer.MAX_VALUE);
    }

    @Test
    public void subscribe_1() throws InterruptedException {
        redisDao.subscribe(new MessageListener() {
            @Override
            public void onMessage(Message message, byte[] pattern) {
                System.out.println("sub_1 : " + message);
            }
        }, "news.1", "news.2");
        Thread.sleep(Integer.MAX_VALUE);
    }

    @Test
    public void publish(){
        Long publish = redisDao.publish("news.2", "msg : news.1");
        System.out.println(publish);
    }


}
