/**
 * @Title RedisUtil.java
 * @Description TODO
 * @author bluecrush
 * @date 2016-7-15上午9:23:41
 * @version v1.1
 */
package com.tauriel.demo.util;

import com.alibaba.druid.filter.config.ConfigTools;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import redis.clients.jedis.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.Map.Entry;


/**
 * @author sth&bluecrush
 * @version v1.1
 * @ClassName: RedisUtil
 * @Description:
 * @date 2016-7-15 上午9:23:41
 */
@Component
@ComponentScan
public class RedisUtil {
    private static final Logger LOG = Logger.getLogger(RedisUtil.class);
    private static JedisPool jedisPool = null;
    private static JedisCluster jedisCluster = null;

    @Value("${redisPublicKey}")
    public String redisPublicKey;

    public static JedisCluster getJedisCluster() {
        return jedisCluster;
    }

    public static void setJedisCluster(JedisCluster jedisCluster) {
        RedisUtil.jedisCluster = jedisCluster;
    }

    private RedisUtil(@Value("${redis.maxTotal}") int maxTotal,
                      @Value("${redis.maxIdle}") int maxIdle,
                      @Value("${redis.maxWaitMillis}") long maxWaitMillis,
                      @Value("${redis.testOnBorrow}") boolean testOnBorrow,
                      @Value("${redis.host}") String host,
                      @Value("${redis.port}") String port,
                      @Value("${redis.timeout}") int timeout,
                      @Value("${redis.password}") String password,
                      @Value("${redisPublicKey}") String redisPublicKey) {
        this.init(maxTotal, maxIdle, maxWaitMillis, testOnBorrow, host, port, timeout, password, redisPublicKey);
    }

    public static JedisPool getJedisPool() {
        return jedisPool;
    }

    public static void setJedisPool(JedisPool jedisPool) {
        RedisUtil.jedisPool = jedisPool;
    }

    /**
     * @Title:getJedis
     * @Description:TODO 从连接池获得redis连接
     * @return:Jedis
     * @author:blueCrush
     * @date:2016-8-3上午9:46:08
     */
    public static synchronized Jedis getJedis() {
        Jedis result = null;
        if (jedisPool!=null){
            result = jedisPool.getResource();
        }
        return result;
    }

    public static boolean isJedis(){
        Jedis jedis = null;
        try {
            jedis = getJedis();
            if (jedis != null){
                return true;
            }
            return false;
        }finally {
            closeJedis(jedis);
        }
    }

    /**
     * @param jedis
     * @Title:closeJedis
     * @Description:TODO 释放连接
     * @return:void
     * @author:blueCrush
     * @date:2016-8-3上午9:46:13
     */
    public static void closeJedis(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }

    public static void hset(String key,String field,String value){
        if (jedisPool != null){
            Jedis jedis = getJedis();
            jedis.hset(key,field,value);
            closeJedis(jedis);
        }else {
            jedisCluster.hset(key,field,value);
        }
    }
    /**
     * @param key
     * @param object
     * @throws IOException
     * @Title:putObject
     * @Description:TODO redis中添加对象
     * @return:void
     * @author:blueCrush
     * @date:2016-8-1下午4:59:41
     */
    public static void putObject(String key, Object object) throws IOException {
        //2.进行对象序列化kryo
        Kryo kryo = new Kryo();
        //支持循环引用，虽然放开会导致速度变慢
        kryo.setReferences(true);
        //禁止注册行为，因为注册行为会导致集群环境下同一个类型为不同整形，从而导致问题
        kryo.setRegistrationRequired(false);
        //如果采用手动注册，就依然认为可以使用注册行为，因此不要存在底下这个操作
//        kryo.register(object.getClass());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Output output = new Output(baos);
        kryo.writeObject(output, object);
        output.flush();
        output.close();
        byte[] byteArray = baos.toByteArray();
        baos.close();
        //3.进行存储
        //1.获得Jedis
        if (jedisPool != null){
            Jedis jedis = getJedis();
            jedis.set(key.getBytes("UTF-8"), byteArray);
            //4.释放连接
            closeJedis(jedis);
        }else {
            jedisCluster.set(key.getBytes("UTF-8"), byteArray);
        }

    }

    /**
     *@description 设置key的过期时间
     *@author 张鑫
     * *@param key
     *@param object
     *@param timeRange
     *@return
     *@throws
     *@create 2018/8/29 10:40
     */
    public static void putObject(String key, Object object,int timeRange) throws IOException {

        //1.获得Jedis
        Jedis jedis = getJedis();
        //2.进行对象序列化kryo
        Kryo kryo = new Kryo();
        kryo.setReferences(true);
        kryo.setRegistrationRequired(false);
//        kryo.register(object.getClass());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Output output = new Output(baos);
        kryo.writeObject(output, object);
        output.flush();
        output.close();
        byte[] byteArray = baos.toByteArray();
        baos.close();
        if (jedisPool != null){
            //3.进行存储
            jedis.set(key.getBytes("UTF-8"), byteArray);
            jedis.expire(key.getBytes(),timeRange);
            //jedis.setex(key.getBytes("UTF-8"), timeRange, byteArray);
            //4.释放连接
            closeJedis(jedis);
        }else {
            jedisCluster.set(key.getBytes("UTF-8"), byteArray);
            jedisCluster.expire(key.getBytes(),timeRange);
        }
    }
    /**
     *@description 设置key失效时间
     *@author weiguang.zhao
     *@param key
     *@param seconds
     *@return void
     *@throws
     *@create 2019/4/19 17:38
     */
    public static void expire(String key,int seconds){
        if (jedisPool != null){
            Jedis jedis = getJedis();
            jedis.expire(key,seconds);
            //3.释放连接
            closeJedis(jedis);
        }else{
            jedisCluster.expire(key,seconds);
        }
    }

    public static void putHashMap(String key, Map<?, ?> map) throws IOException {
        if (jedisPool != null){
            //1.获得Jedi?
            Jedis jedis = getJedis();
            //2.进行Hash存储
            if (map != null && key != null) {
                for (Entry<?, ?> entry : map.entrySet()) {
                    jedis.hset(key.getBytes("UTF-8"), KryoUtil.serialize(entry.getKey()), KryoUtil.serialize(entry.getValue()));
                }
            }
            //3.释放连接
            closeJedis(jedis);
        }else {
            //2.进行Hash存储
            if (map != null && key != null) {
                for (Entry<?, ?> entry : map.entrySet()) {
                    jedisCluster.hset(key.getBytes("UTF-8"), KryoUtil.serialize(entry.getKey()), KryoUtil.serialize(entry.getValue()));
                }
            }
        }

    }

    public static void delete(String key)  {
        if (jedisPool != null){
            //1.获得Jedi?
            Jedis jedis = getJedis();
            //2.进行删除
            Set<String> set = jedis.keys(key);
            Iterator<String> it = set.iterator();
            while (it.hasNext()) {
                String keyStr = it.next();
                jedis.del(keyStr);
            }
            //3.释放连接
            closeJedis(jedis);
        }else {
            //2.进行删除
            Set<String> set = clusterKeys(key);
            Iterator<String> it = set.iterator();
            while (it.hasNext()) {
                String keyStr = it.next();
                jedisCluster.del(keyStr);
            }

        }

    }

    private static Set<String> clusterKeys(String key){
            TreeSet<String> keys = new TreeSet<>();
            Map<String, JedisPool> clusterNodes = jedisCluster.getClusterNodes();
            for(String k : clusterNodes.keySet()){
                JedisPool jp = clusterNodes.get(k);
                Jedis connection = jp.getResource();
                try {
                    keys.addAll(connection.keys(key));
                } catch(Exception e){
                    LOG.error("Getting keys error: {}", e);
                } finally{
                    LOG.debug("Connection closed.");
                    connection.close();//用完一定要close这个链接！！！
                }
            }
            LOG.debug("Keys gotten!");
            return keys;
    }



    public static void hdelete(String key, String... fields) throws IOException {
        if (jedisPool != null){
            //1.获得Jedi?
            Jedis jedis = getJedis();
            //2.进行Hash删除
            jedis.hdel(key, fields);
            //3.释放连接
            closeJedis(jedis);
        }else {
            jedisCluster.hdel(key, fields);
        }

    }

    public static void putHashMap(String key, String field, Object object) throws Exception {
        if (jedisPool != null){
            //1.获得Jedi?
            Jedis jedis = getJedis();
            //2.进行Hash存储
            jedis.hset(key.getBytes("UTF-8"), field.getBytes("UTF-8"), KryoUtil.serialize(object));
            //3.释放连接
            closeJedis(jedis);
        }else {
            jedisCluster.hset(key.getBytes("UTF-8"), field.getBytes("UTF-8"), KryoUtil.serialize(object));
        }

    }

    /**
     * @param key   redis的key
     * @param field hashmap的key
     * @param clazz 返回对象的类型
     * @throws IOException
     * @Title:getHashMapValue
     * @Description:TODO
     * @return:T 返回值
     * @author:blueCrush
     * @date:2016-8-18下午1:52:37
     */
    public static <T> T getHashMapValue(byte[] key, byte[] field, Class<T> clazz) throws IOException {
        T result = null;
        if (jedisPool != null){

            //1.获得Jedi?
            Jedis jedis = getJedis();
            //2.查询redis
            byte[] hget = jedis.hget(key, field);
            //3.反序列化
            result = KryoUtil.deserialize(hget, clazz);
            //4.释放连接
            closeJedis(jedis);
        }else {
            //2.查询redis
            byte[] hget = jedisCluster.hget(key, field);
            //3.反序列化
            result = KryoUtil.deserialize(hget, clazz);
        }
        return result;
    }

    /**
     * @param key   redis的key
     * @param field hashMap的key
     * @param clazz 返回的对象类型
     * @throws IOException
     * @Title:getHashMapValue
     * @Description:TODO
     * @return:T
     * @author:blueCrush
     * @date:2016-8-18下午1:59:38
     */
    public static <T> T getHashMapValue(String key, String field, Class<T> clazz) throws IOException {
        T result = null;
        if (jedisPool != null){
            //1.获得Jedi?
            Jedis jedis = getJedis();
            //2.查询redis
            byte[] hget = jedis.hget(key.getBytes("UTF-8"), field.getBytes("UTF-8"));
            if (hget != null) {
                //3.反序列化
                result = KryoUtil.deserialize(hget, clazz);
            }
            //4.释放连接
            closeJedis(jedis);
        }else {
            byte[] hget = jedisCluster.hget(key.getBytes("UTF-8"), field.getBytes("UTF-8"));
            if (hget != null) {
                //3.反序列化
                result = KryoUtil.deserialize(hget, clazz);
            }
        }
        return result;
    }

    /**
     * redis中存储的格式是Map<String, Map>
     *
     * @param key   大map的key
     * @param field 小map的key
     * @throws Exception
     */
    public static boolean isExitHashMap(String key, String field) throws Exception {
        boolean flag;
        if (jedisPool != null){
            //1.获得Jedi?
            Jedis jedis = getJedis();
            //2.判断Hash是否存在
            flag = jedis.hexists(key, field);
            //3.释放连接
            closeJedis(jedis);
        }else {
            //2.判断Hash是否存在
            flag = jedisCluster.hexists(key, field);
        }
        return flag;
    }

    /**
     * @param key   redis的key
     * @param clazz
     * @return
     * @Title:getObject
     * @Description:TODO redis中获取对象
     * @return:T
     * @author:blueCrush
     * @date:2016-8-1下午5:00:10
     */
    public static <T> T getObject(String key, Class<T> clazz) {
        T result = null;
        try {
            if (jedisPool != null){
                //1.获得Jedis
                Jedis jedis = getJedis();
                //2.通过key找到序列化的value
                byte[] bs = jedis.get(key.getBytes("UTF-8"));
                if (bs != null) {
                    Kryo kryo = new Kryo();
                    kryo.setReferences(true);
                    kryo.setRegistrationRequired(false);
//                    kryo.register(clazz);
                    Input input = new Input(bs);
                    result = kryo.readObject(input, clazz);
                    input.close();
                }
                //3.释放连接
                closeJedis(jedis);
            }else {
                byte[] bs = jedisCluster.get(key.getBytes("UTF-8"));
                if (bs != null) {
                    Kryo kryo = new Kryo();
                    kryo.setReferences(true);
                    kryo.setRegistrationRequired(false);
//                    kryo.register(clazz);
                    Input input = new Input(bs);
                    result = kryo.readObject(input, clazz);
                    input.close();
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * @param
     * @return
     * @throws
     * @description 以key为键放入字符串对象
     * @author lzy
     * @create 2018/5/25 18:29
     */
    public static void put(String key, String object) {
        if (jedisPool != null){
            Jedis jedis = getJedis();
            jedis.set(key, object);
            closeJedis(jedis);
        }else {
            jedisCluster.set(key, object);
        }

    }


    public static String hGetMapField(String key,String field) {
        if(jedisPool != null){
            Jedis jedis = getJedis();
            String value = jedis.hget(key,field);
            closeJedis(jedis);
            return value;
        }else {
            String value = jedisCluster.hget(key,field);
            return value;
        }
    }




    /**
     * @param
     * @return
     * @throws
     * @description 根据传入key获取String的value
     * @author lzy
     * @create 2018/5/25 18:30
     */
    public static String get(String key) {
        if(jedisPool != null){
            Jedis jedis = getJedis();
            String value = jedis.get(key);
            closeJedis(jedis);
            return value;
        }else {
            String value = jedisCluster.get(key);
            return value;
        }


    }

    /**
     * @param
     * @return
     * @throws
     * @description 在redis中清除传入的key值
     * @author lzy
     * @create 2018/5/25 18:31
     */
    public static void del(String key) {
        if(jedisPool != null){
            Jedis jedis = getJedis();
            jedis.del(key);
            closeJedis(jedis);
        }else {
            jedisCluster.del(key);
        }

    }
//带有返回值的删除
    public static long dell(String key) {
        long result;
        if(jedisPool != null){
            Jedis jedis = getJedis();
            result =jedis.del(key);
            closeJedis(jedis);
            return result;
        }else {
            result=jedisCluster.del(key);
            return result;
        }

    }

    /**
     *@description Redis并发计数器功能 ，调用一次数量+1
     *@author 张鑫
     * *@param key
     *@return
     *@throws
     *@create 2018/7/5 16:14
     */
    public static Long incr(String key){
        if(jedisPool != null){
            Jedis jedis = getJedis();
            Long incrIndex = jedis.incr(key);
            closeJedis(jedis);
            return incrIndex;
        }else {
            Long incrIndex = jedisCluster.incr(key);
            return incrIndex;
        }

    }

    /**
     *@description Redis并发计数器递减功能 ，调用一次数量-1
     *@author 张鑫
     * *@param key
     *@return
     *@throws
     *@create 2018/7/5 16:14
     */
    public static Long decr(String key){
        if(jedisPool != null){
            Jedis jedis = getJedis();
            Long incrIndex = jedis.decr(key);
            closeJedis(jedis);
            return incrIndex;
        }else {
            Long incrIndex = jedisCluster.decr(key);
            return incrIndex;
        }

    }

    public static Map<byte[], byte[]> hgetAll(String key){
        Jedis jedis = null;
        try{
            jedis = getJedis();
            Map<byte[], byte[]> stringStringMap = new HashMap<>();
            try {
                stringStringMap = jedis.hgetAll(key.getBytes("UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return stringStringMap;
        }finally {
            closeJedis(jedis);
        }

    }
    public static Map<String,String> hgetAllString(String key){
        Jedis jedis = null;
        try {
           jedis = getJedis();
            Map<String, String> stringStringMap = new HashMap<>();
            stringStringMap = jedis.hgetAll(key);
            return stringStringMap;
        }finally {
            closeJedis(jedis);
        }

    }

    public static  boolean exists(String key){
        Jedis jedis = null;
        try {
            jedis = getJedis();
            Boolean exists = jedis.exists(key);
            return exists;
        }finally {
            closeJedis(jedis);
        }

    }


    private void init(
            int maxTotal,
            int maxIdle,
            long maxWaitMillis,
            boolean testOnBorrow,
            String host,
            String port,
            int timeout,
            String password,
            String redisPublicKey) {
        if (getJedisPool() == null) {
            JedisPoolConfig poolConfig = new JedisPoolConfig();    //已经注入默认值
            poolConfig.setMaxTotal(maxTotal);
            poolConfig.setMaxIdle(maxIdle);
            poolConfig.setMaxWaitMillis(maxWaitMillis);
            poolConfig.setTestOnBorrow(testOnBorrow);
            String [] hosts = host.split(",");
            String[] ports = port.split(",");
            if (hosts.length>1){
                Set<HostAndPort> ipAddres = new HashSet<>();
                for (int i=0;i<hosts.length;i++){
                    ipAddres.add(new HostAndPort(hosts[i],Integer.valueOf(ports[i])));
                }
                if (StringUtils.isEmpty(password)) {
                    //Set<HostAndPort> jedisClusterNode, int connectionTimeout, int soTimeout, int maxAttempts, String password, GenericObjectPoolConfig poolConfig
                    // soTimeout: 返回值的超时时间
                    //maxAttempts：出现异常最大重试次数
                    RedisUtil.setJedisCluster(new JedisCluster(ipAddres,timeout,poolConfig));
                } else {
                    try {
                        password = ConfigTools.decrypt(redisPublicKey, password);
                    } catch (Exception e) {
                        e.printStackTrace();
                        LOG.info("redis密码解密有误");
                    }
                    //RedisUtil.setJedisCluster(new JedisCluster(ipAddres, timeout, 10000, 5, password,poolConfig));
                }
            }else {
                if (StringUtils.isEmpty(password)) {
                    RedisUtil.setJedisPool(new JedisPool(poolConfig, host, Integer.valueOf(port), timeout));
                } else {
                    try {
                        password = ConfigTools.decrypt(redisPublicKey, password);
                    } catch (Exception e) {
                        e.printStackTrace();
                        LOG.info("redis密码解密有误");
                    }
                    RedisUtil.setJedisPool(new JedisPool(poolConfig, host, Integer.valueOf(port), timeout, password));
                }
            }
        }
    }

}
