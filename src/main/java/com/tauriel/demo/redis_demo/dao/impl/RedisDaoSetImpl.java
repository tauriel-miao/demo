package com.tauriel.demo.redis_demo.dao.impl;

import com.tauriel.demo.redis_demo.dao.RedisDao_Set;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service
public class RedisDaoSetImpl implements RedisDao_Set{

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
     * ==> SADD key member [member ...]
     * 将一个或多个 member 元素加入到集合 key 当中，已经存在于集合的 member 元素将被忽略。
     * 假如 key 不存在，则创建一个只包含 member 元素作成员的集合。
     *
     * @return 被添加到集合中的新元素的数量,不包括被忽略的元素
     */
    @Override
    public <K, V> Long sAdd(K key, V... value) {
        return redisTemplate.opsForSet().add(key, value);
    }

    /**
     * ==> SREM key member [member ...]
     * 移除集合 key 中的一个或多个 member 元素，不存在的 member 元素会被忽略。
     *
     * @return 被成功移除的元素的数量，不包括被忽略的元素。
     */
    @Override
    public <K,V> Long sRem(K key, V... value){
        return redisTemplate.opsForSet().remove(key, value);
    }

    /**
     * ==> SPOP key
     * 移除并返回集合中的一个随机元素。
     *
     * @return  被移除的随机元素。
     */
    @Override
    public <K,V> V sPop(K key){
        return (V) redisTemplate.opsForSet().pop(key);
    }

    /**
     * ==> SMOVE source destination member
     * 将 member 元素从 source 集合移动到 destination 集合。
     *
     * @return  如果 member 元素被成功移除，返回 1 。
     * 如果 member 元素不是 source 集合的成员，并且没有任何操作对 destination 集合执行，那么返回 0 。
     */
    @Override
    public <K,V> boolean sMove (K key, V value, K destKey){
        return redisTemplate.opsForSet().move(key, value, destKey);
    }

    /**
     * ==> SCARD key
     *
     * @return  集合中元素的数量
     */
    @Override
    public <K> Long sCard(K key){
        return redisTemplate.opsForSet().size(key);
    }

    /**
     * ==>  SISMEMBER key member
     * 判断 member 元素是否集合 key 的成员。
     *
     * @return 如果 member 元素是集合的成员，返回 1 。
     * 如果 member 元素不是集合的成员，或 key 不存在，返回 0 。
     */
    @Override
    public <K,V> boolean sIsMember(K key, V value){
        return redisTemplate.opsForSet().isMember(key, value);
    }

    /**
     * ==> SINTER key [key ...]
     * 求交集
     *
     * @return 交集成员的列表。
     */
    @Override
    public <K,V> Set<V> sInter(K key, K otherKey){
        return redisTemplate.opsForSet().intersect(key, otherKey);
    }

    @Override
    public <K,V> Set<V> sInter(K key, Collection<K> otherKeys){
        return redisTemplate.opsForSet().intersect(key, otherKeys);
    }

    @Override
    public <K> Long sInterStore(K key, K otherKey, K destKey){
        return redisTemplate.opsForSet().intersectAndStore(key, otherKey, destKey);
    }

    @Override
    public <K> Long sInterStore(K key, Collection<K> otherKeys, K destKey){
        return redisTemplate.opsForSet().intersectAndStore(key, otherKeys, destKey);
    }

    /**
     * ==> SUNION key [key ...]
     * 返回一个集合的全部成员，该集合是所有给定集合的并集。
     *
     * @return 并集成员的列表。
     */
    @Override
    public <K,V> Set<V> sUnion(K key, K otherKey){
        return redisTemplate.opsForSet().union(key, otherKey);
    }

    @Override
    public <K,V> Set<V> sUnion(K key, Collection<K> otherKeys){
        return redisTemplate.opsForSet().union(key, otherKeys);
    }

    /**
     * ==> SUNIONSTORE destination key [key ...]
     * 将结果保存到 destination 集合，而不是简单地返回结果集。
     *
     * @return 结果集中的元素数量。
     */
    @Override
    public <K> long sUnionStore (K key, K otherKey, K destKey){
        return redisTemplate.opsForSet().unionAndStore(key, otherKey, destKey);
    }

    @Override
    public <K> long sUnionStore(K key, Collection<K> otherKeys, K destKey){
        return redisTemplate.opsForSet().unionAndStore(key, otherKeys, destKey);
    }

    /**
     * ==> SDIFF key [key ...]
     * 返回一个集合的全部成员，该集合是所有给定集合之间的差集。(key1-key2)
     *
     * @return 交集成员的列表。
     */
    @Override
    public <K,V> Set<V> sDiff(K key, K otherKey){
        return redisTemplate.opsForSet().difference(key, otherKey);
    }

    @Override
    public <K,V> Set<V> sDiff(K key, Collection<K> otherKeys){
        return redisTemplate.opsForSet().difference(key, otherKeys);
    }

    /**
     * ==> SDIFFSTORE destination key [key ...]
     * 将结果保存到 destination 集合，而不是简单地返回结果集。
     * 如果 destination 集合已经存在，则将其覆盖。
     * destination 可以是 key 本身。
     *
     * @return 结果集中的元素数量。
     */
    @Override
    public <K> long sDiffStore (K key, K otherKey, K destKey){
        return redisTemplate.opsForSet().differenceAndStore(key, otherKey, destKey);
    }

    @Override
    public <K> long sDiffStore(K key, Collection<K> otherKeys, K destKey){
        return redisTemplate.opsForSet().differenceAndStore(key, otherKeys, destKey);
    }

    /**
     * ==> SMEMBERS key
     * 返回集合 key 中的所有成员。
     *
     * @return 集合中的所有成员。
     */
    @Override
    public <K,V> Set<V> sMembers(K key){
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * ==> SRANDMEMBER key [count]
     * 如果命令执行时，只提供了 key 参数，那么返回集合中的一个随机元素。(并不修改集合内容)
     *
     * 如果 count 为正数，且小于集合基数，那么命令返回一个包含 count 个元素的数组，数组中的元素各不相同。如果 count 大于等于集合基数，那么返回整个集合。
     * 如果 count 为负数，那么命令返回一个数组，数组中的元素可能会重复出现多次，而数组的长度为 count 的绝对值。
     *
     * @return 只提供 key 参数时，返回一个元素；如果集合为空，返回 nil 。
     * 如果提供了 count 参数，那么返回一个数组；如果集合为空，返回空数组。
     */
    @Override
    public <K,V> V sRandMember(K key){
        return (V) redisTemplate.opsForSet().randomMember(key);
    }

    /**
     * @param count > 0 （方法内部在调用底层时用的 -count）
     * @return 可返回值相同的数组， 数组长度与count相同
     */
    @Override
    public <K,V> List<V> sRandMember(K key, long count){
        return (List<V>) redisTemplate.opsForSet().randomMembers(key, count);
    }

    /**
     * @param count > 0
     * @return  获取多个key无序集合中的元素（去重），count表示个数
     */
    @Override
    public <K,V> Set<V> distinctRandomMembers(K key, long count){
        return  redisTemplate.opsForSet().distinctRandomMembers(key, count);
    }

}

