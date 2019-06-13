package com.tauriel.demo.redis_demo.dao.impl;

import com.tauriel.demo.redis_demo.dao.RedisDao_Key;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisServerCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.query.SortQuery;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class RedisDaoKeyImpl implements RedisDao_Key {

    @Resource
    public RedisTemplate redisTemplate;


    /**
     * redisTemplate.opsForValue();//操作字符串
     * redisTemplate.opsForHash();//操作hash
     * redisTemplate.opsForList();//操作list
     * redisTemplate.opsForSet();//操作set
     * redisTemplate.opsForZSet();//操作有序set
     */

    /**
     * StringRedisTemplate与RedisTemplate
     * 两者的关系是StringRedisTemplate继承RedisTemplate。
     * 两者的数据是不共通的；也就是说StringRedisTemplate只能管理StringRedisTemplate里面的数据，RedisTemplate只能管理RedisTemplate中的数据。
     * SDR默认采用的序列化策略有两种，一种是String的序列化策略，一种是JDK的序列化策略。
     * StringRedisTemplate默认采用的是String的序列化策略，保存的key和value都是采用此策略序列化保存的。
     * RedisTemplate默认采用的是JDK的序列化策略，保存的key和value都是采用此策略序列化保存的。
     */


    /**
     * ==> DEL key [key ...]
     *
     * @return 是否删除成功
     */
    @Override
    public <K> boolean delete(K key) {
        try {
            redisTemplate.delete(key);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public <K> boolean delete(List<K> keys) {
        try{
            redisTemplate.delete(keys);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 模糊删除
     */
    @Override
    public <K> boolean delKeys(K key) {
        try{
            Set<String> keys = redisTemplate.keys(key);
            if(!CollectionUtils.isEmpty(keys)){
                redisTemplate.delete(keys);
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * ==> EXPIRE key seconds
     * ==> PEXPIRE key milliseconds
     *
     * @return
     */
    @Override
    public <K> boolean expire(K key, long timeout, TimeUnit unit) {
        try{
            redisTemplate.expire(key, timeout, unit);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * ==> EXPIREAT key timestamp  单位：秒
     * ==> PEXPIREAT key milliseconds-timestamp 单位：毫秒
     *
     * @return
     */
    @Override
    public <K> boolean expireAt(final K key, final long timestamp) {
        boolean expire = (boolean) redisTemplate.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                Boolean expire = connection.expireAt(redisTemplate.getKeySerializer().serialize(key), timestamp);
                return expire;
            }
        }, true);
        return expire;
    }

    /**
     * ==> KEYS pattern
     * 查找所有符合给定模式 pattern 的 key 。
     * KEYS * 匹配数据库中所有 key 。
     * KEYS h?llo 匹配 hello ， hallo 和 hxllo 等。
     * KEYS h*llo 匹配 hllo 和 heeeeello 等。
     * KEYS h[ae]llo 匹配 hello 和 hallo ，但不匹配 hillo 。
     *
     * 特殊符号用 \ 隔开
     *
     * @return 符合给定模式的 key 列表。
     */
    @Override
    public Set<String> keys(String pattern) {
        Set keys = redisTemplate.keys(pattern);
        return keys;
    }

    /**
     * ==> EXISTS key
     *
     * @return 若 key 存在，返回 1 ，否则返回 0 。
     */
    @Override
    public <K> boolean exist(final K key) {
        boolean result = (boolean) redisTemplate.execute(new RedisCallback<Boolean>() {
            public Boolean doInRedis(RedisConnection connection)
                    throws DataAccessException {
                RedisSerializer<K> serializer = redisTemplate.getKeySerializer();
                byte[] keyStr = serializer.serialize(key);
                return connection.exists(keyStr);
            }
        });
        return result;
    }

    /**
     * ==> DUMP key
     * 序列化给定 key ，并返回被序列化的值，使用 RESTORE 命令可以将这个值反序列化为 Redis 键。
     * 它带有 64 位的校验和，用于检测错误， RESTORE 在进行反序列化之前会先检查校验和。
     * 值的编码格式和 RDB 文件保持一致。
     * RDB 版本会被编码在序列化值当中，如果因为 Redis 的版本不同造成 RDB 格式不兼容，那么 Redis 会拒绝对这个值进行反序列化操作。
     *
     * @return 如果 key 不存在，那么返回 nil 。否则，返回序列化之后的值
     */
    @Override
    public <K> byte[] dump(K key){
        byte[] dump = redisTemplate.dump(key);
        return dump;
    }

    /**
     * ==> RESTORE key ttl serialized-value
     * 反序列化给定的序列化值，并将它和给定的 key 关联。
     * 参数 ttl 以毫秒为单位为 key 设置生存时间；如果 ttl 为 0 ，那么不设置生存时间。
     *
     * @return 如果反序列化成功那么返回 OK ，否则返回一个错误。
     */
    @Override
    public <K> boolean restore(K key, byte[] value, long timeout, TimeUnit unit){
        try{
            redisTemplate.restore(key, value, timeout, unit);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * ==> MIGRATE host port key destination-db timeout [COPY] [REPLACE]
     * 将 key 原子性地从当前实例传送到目标实例的指定数据库上，一旦传送成功， key 保证会出现在目标实例上，而当前实例上的 key 会被删除。
     *
     * @param option  COPY ：不移除源实例上的 key |  REPLACE ：替换目标实例上已存在的 key
     * @param timeout timeout 参数以毫秒为格式，指定当前实例和目标实例进行沟通的最大间隔时间。这说明操作并不一定要在 timeout 毫秒内完成，只是说数据传送的时间不能超过这个 timeout 数。
     */
    @Override
    public <K> boolean migrate(final K key, final RedisNode node, final int dbIndex, final RedisServerCommands.MigrateOption option, final long timeout){
        try{
            redisTemplate.execute(new RedisCallback() {
                @Override
                public Object doInRedis(RedisConnection connection) throws DataAccessException {
                    connection.migrate(redisTemplate.getKeySerializer().serialize(key), node, dbIndex, option, timeout);
                    return null;
                }
            }, true);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * ==> MOVE key db
     * 将当前数据库的 key 移动到给定的数据库 db 当中。
     *
     * @return
     */
    @Override
    public <K> boolean move(final K key, int dbIndex){
        Boolean move = redisTemplate.move(key, dbIndex);
        return move;
    }

    /**
     * ==> PERSIST key
     * 移除给定 key 的生存时间，将这个 key 从『易失的』(带生存时间 key )转换成『持久的』(一个不带生存时间、永不过期的 key )。
     *
     * @return
     */
    @Override
    public <K> boolean persist(final K key){
        Boolean persist = redisTemplate.persist(key);
        return persist;
    }

    /**
     * ==> TTL key
     * ==> PTTL key
     * 移除给定 key 的生存时间，将这个 key 从『易失的』(带生存时间 key )转换成『持久的』(一个不带生存时间、永不过期的 key )。
     *
     * @return
     */
    @Override
    public <K> Long ttl(final K key){
        Long expire = redisTemplate.getExpire(key);
        return expire;
    }

    /**
     * ==> RANDOMKEY
     *
     * @return 当数据库不为空时，返回一个 key 。
     */
    @Override
    public <K> K randomKey(){
        K key = (K) redisTemplate.randomKey();
        return key;
    }

    /**
     * ==> RENAME key newkey
     * 将 key 改名为 newkey 。
     * 当 key 和 newkey 相同，或者 key 不存在时，返回一个错误。
     * 当 newkey 已经存在时， RENAME 命令将覆盖旧值。
     */
    @Override
    public <K> boolean randomName(K oldKey, K newKey){
        try{
            redisTemplate.rename(oldKey, newKey);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * ==> RENAMENX key newkey
     * 当且仅当 newkey 不存在时，将 key 改名为 newkey 。
     */
    @Override
    public <K> boolean renameNX(K oldKey, K newKey){
        try{
            redisTemplate.renameIfAbsent(oldKey, newKey);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * ==> SORT key [BY pattern] [LIMIT offset count] [GET pattern [GET pattern ...]] [ASC | DESC] [ALPHA] [STORE destination]
     * 最简单的 SORT 使用方法是 SORT key 和 SORT key DESC ：
     * 因为 SORT 命令默认排序对象为数字， 当需要对字符串进行排序时， 需要显式地在 SORT 命令之后添加 ALPHA 修饰符：
     * 排序之后返回元素的数量可以通过 LIMIT 修饰符进行限制， 修饰符接受 offset 和 count 两个参数：
             offset 指定要跳过的元素数量。
            count 指定跳过 offset 个指定的元素之后，要返回多少个对象。
     * 通过使用 BY 选项，可以让 uid 按其他键的元素来排序。
     * 使用 GET 选项， 可以根据排序的结果来取出相应的键值。
     * 可以用 # 获取被排序键的值。
     * STORE 选项指定一个 key 参数，可以将排序结果保存到给定的键上。
     *
     * @return
     */
    @Override
    public <K> List sort(SortQuery sortQuery){
        List sort = redisTemplate.sort(sortQuery);
        return sort;
    }





}
