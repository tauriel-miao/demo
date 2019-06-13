package com.tauriel.demo.redis_demo.test;

import com.tauriel.demo.redis_demo.dao.RedisDao_String;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

//@Component
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-context.xml" })
public class StringTest {

    @Autowired
    public RedisDao_String redisDao;

    @Test
    public void addString(){
        boolean b = redisDao.set("redis", "test");
        System.out.println("addString = " + b);
    }

    @Test
    public void addString_expire(){
        boolean b = redisDao.setEx("redis_expire", "expire", 5, TimeUnit.SECONDS);
        System.out.println("addString_expire = " + b);
    }

    @Test
    public void addString_overwrite(){
        boolean b = redisDao.setRange("redis", "world", 5);
        System.out.println("addString_overwrite = " + b);

        System.out.println("getString = " + redisDao.get("redis"));

        redisDao.setRange("redis", "hello", 0);
    }

    @Test
    public void addString_ifNotExist(){
        boolean b = redisDao.setNx("redis", "redis");
        System.out.println("addString_ifNotExist" + b);
    }

    @Test
    public void muti_addString(){
        HashMap<String, String> map = new HashMap<>();
        map.put("student_1", "liu");
        map.put("student_2", "iu");
        map.put("student_3", "li");
        boolean b = redisDao.mSet(map);
        System.out.println("muti_addString = " + b);
    }

    @Test
    public void muti_addString_ifNotExist(){
        HashMap<String, String> map = new HashMap<>();
        map.put("student_1", "iu");
        map.put("student_4", "ren");
        map.put("student_3", "liu");
        boolean b = redisDao.mSetNx(map);
        System.out.println("muti_addString_ifNotExist = " + b);
    }

    @Test
    //@After
    public void get(){
        String str = redisDao.get("redis");
        System.out.println(str);
        String str1 = redisDao.get("student_4");
        System.out.println(str1);
/*        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(redisDao.get("redis_expire"));*/
    }

    @Test
    public void get_set(){
        String set = redisDao.getSet("redis", "44444");
        System.out.println("get_set = " + set);
    }

    @Test
    public void muti_getString(){
        List<Object> objects = redisDao.mGet(Arrays.asList(new String[]{"student_1", "student_5", "student_2"}));
        System.out.println(" muti_getString = " + objects);
    }

    @Test
    public void increment_string_long(){
        Long redis = redisDao.incrBy("redis", 20L);
        System.out.println("increment_string_long = " + redis);
    }


    @Test
    public void increment_string_double(){
        Double redis = redisDao.incrByFloat("redis", 20.1243);
        System.out.println("increment_string_double = " + redis);
    }

    @Test
    public void append_string(){
        Integer size= redisDao.append("redis", " world");
        System.out.println(" append_string = " + size);
    }

    @Test
    public void trim_string(){
        String redis = redisDao.getRange("redis", 0, 5);
        System.out.println(" trim_string = " + redis);
    }

    @Test
    public void size_string(){
        Long size = redisDao.strLen("redis");
        System.out.println(" size = " + size);
    }

    @Test
    public void setBit(){
        //转换为二进制为 0011 0000
        redisDao.set("bit", "0");
        System.out.println((char[]) redisDao.get("bit"));
        boolean bit = redisDao.setBit("bit", 6, true);
        System.out.println("setBit = " + bit);
        System.out.println((char[]) redisDao.get("bit"));
    }

    @Test
    public void getBit(){
        boolean bit = redisDao.getBit("bit", 6);
        System.out.println("getBit = " + bit);
    }

    @Test
    public void bitcount(){
        Long bit = redisDao.bitCount("bit");
        System.out.println("bitcount = " + bit);
    }



}
