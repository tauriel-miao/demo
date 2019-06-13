package com.tauriel.demo.redis_demo.dao.impl;

import com.tauriel.demo.redis_demo.dao.RedisDao_ZSet;
import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

@Service
public class RedisDaoZSetImpl implements RedisDao_ZSet{

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
     * ==> ZADD key score member [[score member] [score member] ...]
     *
     * 将一个或多个 member 元素及其 score 值加入到有序集 key 当中。
     * 如果某个 member 已经是有序集的成员，那么更新这个 member 的 score 值，并通过重新插入这个 member 元素，来保证该 member 在正确的位置上。
     * score 值可以是整数值或双精度浮点数。
     * 如果 key 不存在，则创建一个空的有序集并执行 ZADD 操作。
     * 当 key 存在但不是有序集类型时，返回一个错误。
     *
     * @return
     */
    @Override
    public <K, V> boolean zAdd(K key, V member, double score) {
        Boolean isAdd = redisTemplate.opsForZSet().add(key, member, score);
        return isAdd;
    }

    @Override
    public <K> long zAdd(K key, Set<ZSetOperations.TypedTuple<Serializable>> tuples) {
        Long addCount = redisTemplate.opsForZSet().add(key, tuples);
        return addCount;
    }

    @Override
    public <K, T> long zAdd(K key, Map<Double, T> map) {
        int i = 0;
        for (Double num: map.keySet()){
            boolean isAdd = zAdd(key, map.get(num), num);
            if(isAdd){
                i ++;
            }
        }
        return i;
    }

    /**
     * ==>  ZRANGE key start stop [WITHSCORES]
     * 返回有序集 key 中，指定区间内的成员。
     * 其中成员的位置按 score 值递增(从小到大)来排序。
     * 具有相同 score 值的成员按字典序(lexicographical order )来排列。
     * 如果你需要成员按 score 值递减(从大到小)来排列，请使用 ZREVRANGE 命令。
     * 下标参数 start 和 stop 都以 0 为底，也就是说，以 0 表示有序集第一个成员，以 1 表示有序集第二个成员，以此类推。
     * 你也可以使用负数下标，以 -1 表示最后一个成员， -2 表示倒数第二个成员，以此类推。
     *
     * @return  指定区间内，带有 score 值(可选)的有序集成员的列表。
     */
    @Override
    public <K,T> Set<T> zRange(K key, long start, long end) {
        Set<T> valueSet = redisTemplate.opsForZSet().range(key, start, end);
        return valueSet;
    }

    /**
     * ==>  ZRANGEBYSCORE key min max [WITHSCORES] [LIMIT offset count]
     *
     * 返回有序集 key 中，所有 score 值介于 min 和 max 之间(包括等于 min 或 max )的成员。有序集成员按 score 值递增(从小到大)次序排列。
     * 具有相同 score 值的成员按字典序(lexicographical order)来排列(该属性是有序集提供的，不需要额外的计算)。
     * 可选的 LIMIT 参数指定返回结果的数量及区间(就像SQL中的 SELECT LIMIT offset, count )，注意当 offset 很大时，定位 offset 的操作可能需要遍历整个有序集，此过程最坏复杂度为 O(N) 时间。
     * 可选的 WITHSCORES 参数决定结果集是单单返回有序集的成员，还是将有序集成员及其 score 值一起返回。
     *
     * @return  指定区间内，带有 score 值(可选)的有序集成员的列表。
     */
    @Override
    public <K> Set<RedisZSetCommands.Tuple> zRangeByScore(K key, long start, long end) {
        Set set = redisTemplate.opsForZSet().rangeByScore(key, start, end);
        return set;
    }

    @Override
    public <K> Set<RedisZSetCommands.Tuple> zRangeByScore(K key, long start, long end, double mix, double max) {
        Set set = redisTemplate.opsForZSet().rangeByScore(key, mix, max, start, end);
        return set;
    }

    /**
     * ==>  ZREVRANGE key start stop [WITHSCORES]
     *
     * 返回有序集 key 中，指定区间内的成员。
     * 其中成员的位置按 score 值递减(从大到小)来排列。
     * 具有相同 score 值的成员按字典序的逆序(reverse lexicographical order)排列。
     *
     * @return  指定区间内，带有 score 值(可选)的有序集成员的列表。
     */
    @Override
    public <K> Set<RedisZSetCommands.Tuple> zRevRange(K key, long start, long end) {
        Set set = redisTemplate.opsForZSet().reverseRange(key, start, end);
        return set;
    }

    /**
     * ==> ZREVRANGEBYSCORE key max min [WITHSCORES] [LIMIT offset count]
     *
     * 返回有序集 key 中， score 值介于 max 和 min 之间(默认包括等于 max 或 min )的所有的成员。有序集成员按 score 值递减(从大到小)的次序排列。
     * 具有相同 score 值的成员按字典序的逆序(reverse lexicographical order )排列。
     *
     * @return  指定区间内，带有 score 值(可选)的有序集成员的列表。
     */
    @Override
    public <K> Set<RedisZSetCommands.Tuple> zRangeByScore(K key, double start, double end) {
        Set set = redisTemplate.opsForZSet().reverseRangeByScore(key, start, end);
        return set;
    }

    @Override
    public Set<RedisZSetCommands.Tuple> zRangeByScore(String key, long start, long end, double mix, double max) {
        Set set = redisTemplate.opsForZSet().reverseRangeByScore(key, mix, max, start, end);
        return set;
    }

    /**
     *  ==>ZCARD key
     *
     * 返回有序集 key 的基数。
     *
     * @return 当 key 存在且是有序集类型时，返回有序集的基数。当 key 不存在时，返回 0 。
     */
    @Override
    public <K> Long zCard(K key) {
        Long num = redisTemplate.opsForZSet().zCard(key);
        return num;
    }

    /**
     * ==> ZCOUNT key min max
     *
     * 返回有序集 key 中， score 值在 min 和 max 之间(默认包括 score 值等于 min 或 max )的成员的数量。
     *
     * @return  score 值在 min 和 max 之间的成员的数量。
     */
    @Override
    public <K> long zCount(K key, double mix, double max) {
        Long num = redisTemplate.opsForZSet().count(key, mix, max);
        return num;
    }

    /**
     * ==> ZINCRBY key increment member
     *
     * 为有序集 key 的成员 member 的 score 值加上增量 increment 。
     * 可以通过传递一个负数值 increment ，让 score 减去相应的值，比如 ZINCRBY key -5 member ，就是让 member 的 score 值减去 5 。
     * 当 key 不存在，或 member 不是 key 的成员时， ZINCRBY key increment member 等同于 ZADD key increment member 。
     * 当 key 不是有序集类型时，返回一个错误。
     * score 值可以是整数值或双精度浮点数。
     *
     * @return  member 成员的新 score 值，以字符串形式表示。
     */
    @Override
    public <K,V> Double zIncrBy(K key, V value, double delta) {
        Double num = redisTemplate.opsForZSet().incrementScore(key, value, delta);
        return num;
    }

    /**
     *  ==>  ZRANK key member
     *
     * 返回有序集 key 中成员 member 的排名。其中有序集成员按 score 值递增(从小到大)顺序排列。
     * 排名以 0 为底，也就是说， score 值最小的成员排名为 0 。
     *
     * @return 如果 member 是有序集 key 的成员，返回 member 的排名。如果 member 不是有序集 key 的成员，返回 nil 。
     */
    @Override
    public <K,V> Long zRank(K key, V value) {
        Long index = redisTemplate.opsForZSet().rank(key, value);
        return index;
    }

    /**
     *  ==>  ZREVRANK key member
     *
     *  返回有序集 key 中成员 member 的排名。其中有序集成员按 score 值递减(从大到小)排序。
     * 排名以 0 为底，也就是说， score 值最大的成员排名为 0 。
     *
     * @return 如果 member 是有序集 key 的成员，返回 member 的排名。如果 member 不是有序集 key 的成员，返回 nil 。
     */
    @Override
    public <K,V> Long zRevRank(K key, V value) {
        Long index = redisTemplate.opsForZSet().reverseRank(key, value);
        return index;
    }

    /**
     *  ==>  ZREM key member [member ...]
     *
     *  移除有序集 key 中的一个或多个成员，不存在的成员将被忽略。
     * 当 key 存在但不是有序集类型时，返回一个错误。
     *
     * @return  被成功移除的成员的数量，不包括被忽略的成员。
     */
    @Override
    public <K,V> Long zRem(K key, V value) {
        Long index = redisTemplate.opsForZSet().remove(key, value);
        return index;
    }

    /**
     *  ==>  ZREM key member [member ...]
     *
     *  移除有序集 key 中的一个或多个成员，不存在的成员将被忽略。
     * 当 key 存在但不是有序集类型时，返回一个错误。
     *
     * @return  被成功移除的成员的数量，不包括被忽略的成员。
     */
    @Override
    public <K,V> Long zRem(K key, V... value) {
        Long index = redisTemplate.opsForZSet().remove(value);
        return index;
    }

    /**
     * ==>  ZREMRANGEBYRANK key start stop
     *
     * 移除有序集 key 中，指定排名(rank)区间内的所有成员。
     * 区间分别以下标参数 start 和 stop 指出，包含 start 和 stop 在内。
     * 下标参数 start 和 stop 都以 0 为底，也就是说，以 0 表示有序集第一个成员，以 1 表示有序集第二个成员，以此类推。
     * 你也可以使用负数下标，以 -1 表示最后一个成员， -2 表示倒数第二个成员，以此类推。
     *
     * @return  被移除成员的数量。
     */
    @Override
    public <K,V> Long zRemRangeByRank(K key, long start, long end) {
        Long index = redisTemplate.opsForZSet().removeRange(key, start, end);
        return index;
    }

    /**
     * ==>  ZREMRANGEBYSCORE key min max
     *
     *  移除有序集 key 中，所有 score 值介于 min 和 max 之间(包括等于 min 或 max )的成员。
     *
     * @return  被移除成员的数量。
     */
    @Override
    public <K,V> Long zRemRangeByScore(K key, double start, double end) {
        Long index = redisTemplate.opsForZSet().removeRangeByScore(key, start, end);
        return index;
    }

    /**
     *  ==>  ZSCORE key member
     *
     *  返回有序集 key 中，成员 member 的 score 值。
     * 如果 member 元素不是有序集 key 的成员，或 key 不存在，返回 nil 。
     *
     * @return  member 成员的 score 值，以字符串形式表示。
     */
    @Override
    public <K,V> Double zScore(K key, V value) {
        Double index = redisTemplate.opsForZSet().score(key, value);
        return index;
    }

    /**
     *  ==>  ZUNIONSTORE destination numkeys key [key ...] [WEIGHTS weight [weight ...]] [AGGREGATE SUM|MIN|MAX]
     *
     *  计算给定的一个或多个有序集的并集，其中给定 key 的数量必须以 numkeys 参数指定，并将该并集(结果集)储存到 destination 。
     * 默认情况下，结果集中某个成员的 score 值是所有给定集下该成员 score 值之 和 。
     *
     * WEIGHTS  :
     * 使用 WEIGHTS 选项，你可以为 每个 给定有序集 分别 指定一个乘法因子(multiplication factor)，每个给定有序集的所有成员的 score 值在传递给聚合函数(aggregation function)之前都要先乘以该有序集的因子。
     * 如果没有指定 WEIGHTS 选项，乘法因子默认设置为 1 。
     *
     * AGGREGATE  :
     * 使用 AGGREGATE 选项，你可以指定并集的结果集的聚合方式。
     * 默认使用的参数 SUM ，可以将所有集合中某个成员的 score 值之 和 作为结果集中该成员的 score 值；使用参数 MIN ，可以将所有集合中某个成员的 最小 score 值作为结果集中该成员的 score 值；而参数 MAX 则是将所有集合中某个成员的 最大 score 值作为结果集中该成员的 score 值。
     *
     * @return 保存到 destination 的结果集的基数。
     */
    @Override
    public <K,S,T> Long zUnionStore(K key, T otherKey, S destKey) {
        Long index = redisTemplate.opsForZSet().unionAndStore(key, otherKey, destKey);
        return index;
    }

    @Override
    public <K,V> Long zUnionStore(K key, Collection<K> otherKeys, K destKey) {
        Long index = redisTemplate.opsForZSet().unionAndStore(key, otherKeys, destKey);
        return index;
    }

    /**
     *  ==>  ZINTERSTORE destination numkeys key [key ...] [WEIGHTS weight [weight ...]] [AGGREGATE SUM|MIN|MAX]
     *
     *  计算给定的一个或多个有序集的交集，其中给定 key 的数量必须以 numkeys 参数指定，并将该交集(结果集)储存到 destination 。
     * 默认情况下，结果集中某个成员的 score 值是所有给定集下该成员 score 值之和.
     *
     * @return  保存到 destination 的结果集的基数。
     */
    @Override
    public <K,V> Long zInterStore(K key, K otherKey, K destKey) {
        Long index = redisTemplate.opsForZSet().intersectAndStore(key, otherKey, destKey);
        return index;
    }

    @Override
    public <K,V> Long zInterStore(K key, Collection<K> otherKeys, K destKey) {
        Long index = redisTemplate.opsForZSet().intersectAndStore(key, otherKeys, destKey);
        return index;
    }




}
