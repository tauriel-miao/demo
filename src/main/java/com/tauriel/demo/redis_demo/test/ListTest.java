package com.tauriel.demo.redis_demo.test;

import com.tauriel.demo.redis_demo.dao.RedisDao_List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisListCommands;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;
import java.util.concurrent.TimeUnit;

//@Component
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-context.xml" })
public class ListTest {

    @Autowired
    public RedisDao_List redisDao;

    public static final String KEY = "key_list";

    @Test
    public void getList() {
        List<Object> list = redisDao.lRange(KEY, 0, -1);
        System.out.println(list);
    }

    @Test
    public void trim_list(){
        boolean b = redisDao.lTrim(KEY, 4, 6);
        System.out.println(b);
    }

    @Test
    public void size_list(){
        Long size = redisDao.lLen(KEY);
        System.out.println(size);
    }

    @Test
    public void leftPush(){
        Long haha = redisDao.lPush(KEY, "left_1");
        System.out.println(haha);
    }

    @Test
    public void leftPushAll(){
        Long haha = redisDao.lPush(KEY, "left_1", "left_7", "left_8");
        System.out.println(haha);
    }

    @Test
    public void rightPush(){
        Object o = redisDao.rPush(KEY, "right_1");
        System.out.println(o);
    }

    @Test
    public void rightPushAll(){
        ArrayList<String> list = new ArrayList<>();
        list.add("right_pushall_2");
        list.add("right_pushall_4");
        list.add("right_pushall_5");
        Object o = redisDao.rPush(KEY, list.toArray());
        System.out.println(o);
    }

    @Test
    public void leftPush_ifNotExist(){
        Long exist = redisDao.lPushX(KEY, "left_1");
        System.out.println(exist);
    }

    @Test
    public void rightPush_ifNotExist(){
        Long exist = redisDao.rPushX(KEY, "right_1");
        System.out.println(exist);
    }

    @Test
    public void setList_index(){
        boolean middle_index = redisDao.lSet(KEY, 3, "middle_index");
        System.out.println(middle_index);
    }

    @Test
    public void remove(){
        Long right_1 = redisDao.lRem(KEY, 1, "right_1");
        System.out.println(right_1);
    }

    @Test
    public void index_list(){
        Object o = redisDao.lIndex(KEY, 1);
        System.out.println(o);
    }

    @Test
    public void leftPop(){
        Object o = redisDao.lPop(KEY);
        System.out.println(o);
    }

    @Test
    public void leftPop_expire(){
        Object o = redisDao.lPop(KEY + "_expire", 3, TimeUnit.SECONDS);
        System.out.println(o);
    }

    @Test
    public void rightPop(){
        Object o = redisDao.rPop(KEY);
        System.out.println(o);
    }

    @Test
    public void rightPop_expire(){
        Object o = redisDao.rPop(KEY + "_expire", 3, TimeUnit.SECONDS);
        System.out.println(o);
    }

    @Test
    public void rightPopAndLeftPush(){
        Object test_key = redisDao.rPopLPush(KEY, "test_key");
        System.out.println(test_key);
    }

    @Test
    public void rightPopAndLeftPush_expire(){
        Object test_key = redisDao.rPopLPush(KEY, "test_key", 5, TimeUnit.SECONDS);
        System.out.println(test_key);
    }

    @Test
    public void insert_list(){
        Long insert = redisDao.lInsert("test_key", RedisListCommands.Position.AFTER, "shaodenghaha", "haha");
        System.out.println(insert);
    }


}
