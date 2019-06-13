package com.tauriel.demo.redis_demo.test;

import com.tauriel.demo.redis_demo.dao.RedisDao_Hash;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

//@Component
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-context.xml" })
public class HashTest {

    @Autowired
    public RedisDao_Hash redisDao;

    @Test
    public void addHash(){
        boolean redis = redisDao.hSet("redis", "4.0", "redis4.0");
        System.out.println(redis);
    }

    @Test
    public void addHash_map(){
        HashMap<String, String> map = new HashMap<>();
        map.put("student_1", "liu");
        map.put("student_2", "iu");
        map.put("student_3", "li");
        boolean redis = redisDao.hMSet("redis", map);
        System.out.println(redis);
    }

    @Test
    public void addHash_ifNotExist(){
        boolean ex = redisDao.hSetNX("redis", "4.0", "haha");
        System.out.println(ex);
    }

    @Test
    public void hasHashKey(){
        boolean hashKey = redisDao.hExists("redis", "student_1");
        System.out.println(hashKey);
    }

    @Test
    public void delete(){
        Long delete = redisDao.hDel("redis", "student_1", "4.0");
        System.out.println(delete);
    }

    @Test
    public void getHash(){
        Object hash = redisDao.hGet("redis", "student_3");
        System.out.println(hash);
    }

    @Test
    public void muti_getHash(){
        ArrayList<String> list = new ArrayList<>();
        list.add("student_3");
        list.add("student_4");
        List<String> list1 = redisDao.hMGet("redis", list);
        System.out.println(list1);
    }

    @Test
    public void getEntries_hash(){
        Map<Object, Object> en = redisDao.hGetAll("redis");
        System.out.println(en);
    }

    @Test
    public void keys_hash(){
        Set<String> redis = redisDao.hKeys("redis");
        System.out.println(redis);
    }

    @Test
    public void len_hash(){
        Long count = redisDao.hLen("redis");
        System.out.println(count);
    }

    @Test
    public void vals_hash(){
        List<Object> vals = redisDao.hVals("redis");
        System.out.println(vals);
    }

    @Test
    public void increment_hash_long(){
        Long value = redisDao.hIncrBy("redis", "arg1", 10);
        System.out.println(value);
    }

    @Test
    public void increment_hash_double(){
        Double value = redisDao.hIncrByFloat("redis", "arg2", 1.2);
        System.out.println(value);
    }




}
