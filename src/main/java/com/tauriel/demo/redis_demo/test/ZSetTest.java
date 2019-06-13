package com.tauriel.demo.redis_demo.test;

import com.tauriel.demo.redis_demo.dao.RedisDao_List;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//@Component
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-context.xml" })
public class ZSetTest {

    @Autowired
    public RedisDao_List redisDao;




}
