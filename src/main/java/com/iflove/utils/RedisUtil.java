package com.iflove.utils;

import com.iflove.entity.Const;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 */
@Component
public class RedisUtil {
    @Resource
    private StringRedisTemplate template;

    /**
     * 清空全部缓存
     */
    public void flushAll() {
        template.getConnectionFactory().getConnection().flushAll();
    }

    /**
     * 将 Token 列入 Redis 白名单
     * @param key uuid
     * @param expire 过期时间
     */
    public void set(String key, long expire) {
        template.opsForValue().set(Const.JWT_WHITE_LIST + key, "", expire, TimeUnit.MILLISECONDS);
    }

    /**
     * 将 Token 列入 Redis 黑名单
     * @param key Token 特有uuid
     * @return 成功？
     */
    public Boolean delete(String key) {
        template.delete(Const.JWT_WHITE_LIST + key);
        template.opsForValue().set(Const.JWT_BLACK_LIST + key, "");
        return true;
    }

    /**
     * 判断是否存在黑名单中（是否有效）
     * @param key uuid
     * @return 有效？
     */
    public Boolean isInvalid(String key) {
        return template.hasKey(Const.JWT_BLACK_LIST + key);
    }

    /**
     * 设定过期时间
     * @return 过期时间
     */
    public Date expireTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, Const.EXPIRE_TIME);
        return calendar.getTime();
    }

    /**
     * zset逆向排序
     * @param key key
     * @return 通过索引区间返回有序集合成指定区间内的成员对象，其中有序集成员按分数值递减(从大到小)顺序排列
     */
    public Set<String> zsreverseRange(String key){
        return template.opsForZSet().reverseRange(key, 0, -1);
    }

    /**
     * 获取所有字段和值
     * @param key key
     * @return 通过索引区间返回有序集合成指定区间内的成员对象，其中有序集成员按分数值递增(从小到大)顺序排列
     */
    public Set<ZSetOperations.TypedTuple<String>> zsRangeWithScores(String key) {
        return template.opsForZSet().rangeWithScores(key, 0, -1);
    }

    /**
     * zset 存储点赞量，点击量
     * 注意：仅供服务器启动读取数据使用
     * @param key key
     * @param value id
     * @param score 分数
     */
    public void zsAdd(String key, String value, Double score) {
        template.opsForZSet().add(key, value, score);
    }

    /**
     * zset存储点击量，点赞量
     * @param key key
     * @param value  属性值
     * @param delta  增加值
     */
    public void zsIncr(String key, String value, Integer delta){
        try {
            // 按照积分排序，如果积分相同，按照更新时间排序, 新加入的更大, score = 积分 + 时间戳 / 1e13
            // 查询是否已有值，有则更新，无则添加
            Double existValue = template.opsForZSet().score(key, value);
            double score = delta; // 整数数值
            if (existValue != null) {
                // 存在，需要删除
                template.opsForZSet().remove(key, value);
                score += existValue.intValue();
            }
            // 更新时间戳
            score += System.currentTimeMillis() / 1e13;
            template.opsForZSet().incrementScore(key, value, score);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 返回对应的score
     * 注意：template返回值的value携带时间戳，需去掉
     * @param key key
     * @param value value
     * @return score
     */
    public int zsScore(String key, String value) {
        Double score = template.opsForZSet().score(key, value);
        return score != null ? score.intValue() : 0;
    }

    /**
     * 拼接用户id和被点赞的视频id作为key。格式 222222::333333
     * @param userId 用户id
     * @param videoId 视频id
     * @return key
     */
    private String mapId2Key(String userId, String videoId) {
        return userId + "::" + videoId;
    }

    /**
     * 根据key和hashkey查找是否点赞(1 点赞 / null & 0 未点赞)
     * @param key key
     * @param userId 用户id
     * @param videoId 视频id
     * @return 是否点赞(1 点赞 / 0 未点赞)
     */
    public int hGetLike(String key, String userId, String videoId) {
        Object o = template.opsForHash().get(key, this.mapId2Key(userId, videoId));
        if (o == null) return 0;
        return Integer.parseInt((String) o);
    }

    /**
     * 用户点赞/取消点赞视频
     * @param key key
     * @param userId 用户id
     * @param videoId 视频id
     * @param actionType 0 取消点赞/ 1 点赞
     */
    public void hSaveLike(String key, String userId, String videoId, int actionType) {
        template.opsForHash().put(key, this.mapId2Key(userId, videoId), String.valueOf(actionType));
    }

    /**
     * 获得用户的点赞列表
     * @param key key
     * @param userId 用户id
     * @return 点赞列表（videoId）
     */
    public List<Long> hGetLikeList(String key, String userId) {
        Map<Object, Object> entries = template.opsForHash().entries(key);
        List<Long> result = new ArrayList<>();
        entries.forEach((k, v) -> {
            String sk = String.valueOf(k);
            int iv = Integer.parseInt((String) v);
            // 如果是该用户，且点赞了视频
            if (Objects.equals(sk.split("::")[0], userId) && iv == 1) {
                result.add(Long.valueOf(sk.split("::")[1]));
            }
        });
        return result;
    }

    /**
     * 获取该key下的所有点赞关系
     * @param key key
     * @return map
     */
    public Map<Object, Object> hGetAllLikeList(String key) {
        return template.opsForHash().entries(key);
    }
}
