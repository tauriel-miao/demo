package com.tauriel.demo.redis_demo.test;

import com.tauriel.demo.redis_demo.dao.RedisDao_Set;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.Set;

//@Component
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-context.xml" })
public class SetTest {

    @Autowired
    public RedisDao_Set redisDao;

    public static final String KEY = "key_set";

    @Test
    public void addSet() {
        Long count = redisDao.sAdd(KEY, "set_1", "set_2", "set_3", "set_4");
        System.out.println(count);
    }

    @Test
    public void remove(){
        Long count = redisDao.sRem(KEY, "set_1");
        System.out.println(count);
    }

    @Test
    public void popSet(){
        Object set = redisDao.sPop(KEY);
        System.out.println(set);
    }

    @Test
    public void move (){
        boolean flag = redisDao.sMove(KEY, "set_1", "test_set");
        System.out.println(flag);
    }

    @Test
    public void size_set(){
        Long size = redisDao.sCard(KEY);
        System.out.println(size);
    }

    @Test
    public void isMemeber(){
        boolean set_4 = redisDao.sIsMember(KEY, "set_4");
        System.out.println(set_4);
    }

    @Test
    public void intersect(){
        Set<Object> set = redisDao.sInter(KEY, "test_set");
        System.out.println(set);
    }

    @Test
    public void intersect_muti(){
        Set<Object> obj = redisDao.sInter(KEY, Arrays.asList(new String[]{"haha", "test_set"}));
        System.out.println(obj);
    }

    @Test
    public void intersectAndStore(){
        Long size = redisDao.sInterStore(KEY, "test_set", "store");
        System.out.println(size);
    }

    @Test
    public void intersectAndStore_muti(){
        Long obj = redisDao.sInterStore(KEY, Arrays.asList(new String[]{"haha", "test_set"}), "store");
        System.out.println(obj);

    }

    @Test
    public void union(){
        Set<Object> set = redisDao.sUnion(KEY, "test_set");
        System.out.println(set);
    }

    @Test
    public void union_muti(){
        Set<Object> obj = redisDao.sUnion(KEY, Arrays.asList(new String[]{"haha", "test_set"}));
        System.out.println(obj);
    }

    @Test
    public void unionAndStore (){
        Long obj = redisDao.sUnionStore(KEY, "test_set", "store");
        System.out.println(obj);
    }

    @Test
    public void unionAndStore_muti(){
        Long obj = redisDao.sUnionStore(KEY, Arrays.asList(new String[]{"haha", "test_set"}), "store");
        System.out.println(obj);
    }

    @Test
    public void difference(){
        Set<Object> obj = redisDao.sDiff(KEY, "haha");
        System.out.println(obj);
    }

    @Test
    public void difference_muti(){
        Set<Object> obj = redisDao.sDiff(KEY, Arrays.asList(new String[]{"haha", "test_set"}));
        System.out.println(obj);
    }

    @Test
    public void differenceAndStore (){
        Long obj = redisDao.sDiffStore(KEY, "haha", "store");
        System.out.println(obj);
    }

    @Test
    public void differenceAndStore_muti(){
        Long obj = redisDao.sUnionStore(KEY, Arrays.asList(new String[]{"haha", "test_set"}), "store");
        System.out.println(obj);
    }

    @Test
    public void members(){
        Set<Object> set = redisDao.sMembers(KEY);
        System.out.println(set);
    }

    @Test
    public void randomMember(){
        Object obj = redisDao.sRandMember(KEY);
        System.out.println(obj);
    }

    @Test
    public void randomMembers(){
        Object obj = redisDao.sRandMember(KEY, 10);
        System.out.println(obj);
    }

    @Test
    public void distinctRandomMembers(){
        Set<Object> objects = redisDao.distinctRandomMembers(KEY, 10);
        System.out.println(objects);
    }





}
