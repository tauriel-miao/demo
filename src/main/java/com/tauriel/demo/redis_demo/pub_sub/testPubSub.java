package com.tauriel.demo.redis_demo.pub_sub;

import com.tauriel.demo.redis_demo.dao.RedisDao_Pub_Sub;
import com.tauriel.demo.redis_demo.pub_sub.client.PubClient;
import com.tauriel.demo.redis_demo.pub_sub.client.SubClient;
import com.tauriel.demo.redis_demo.pub_sub.client.SubClient_2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-context.xml" })
public class testPubSub {

    @Autowired
    public RedisMessageListenerContainer redisMessageListenerContainer;
    @Autowired
    public RedisDao_Pub_Sub redisDao;

    @Test
    public void publish1(){
        PubClient pubClient = new PubClient("pub-1", SubClient.WarnEnum.forward, redisDao);
        pubClient.pub("sub-1", "hello~");
    }

    @Test
    public void publish2(){
        PubClient pubClient = new PubClient("pub-2", SubClient.WarnEnum.forward, redisDao);
        pubClient.pub("sub-2", "world...");
    }

    @Test
    public void subscribe1() throws InterruptedException {
        String channal = "sub-1";
        SubClient subClient = new SubClient("session-1", channal, SubClient.WarnEnum.forward, redisMessageListenerContainer);
        subClient.sub(channal);
        Thread.sleep(40000);
        System.out.println(" 40s 时间结束， 取消订阅");
        subClient.unsubscribe_channel(channal);
        Thread.sleep(Integer.MAX_VALUE);
    }


    /**
     * TODO  暂时未解决的问题  ：  无法动态进行模式订阅
     * @throws InterruptedException
     */
    @Test
    public void subscribe2() throws InterruptedException {
        String channal = "sub-2";
        SubClient_2 subClient = new SubClient_2("session-2", redisMessageListenerContainer);
/*        subClient.sub(channal);
        Thread.sleep(20000);
        System.out.println("20s 时间结束， 模式订阅开始~");
        subClient.unsubscribe_channel(channal);*/
        //subClient.sub_pattern("sub*");
        Thread.sleep(Integer.MAX_VALUE);
    }
}
