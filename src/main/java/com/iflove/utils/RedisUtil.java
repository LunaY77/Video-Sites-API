package com.iflove.utils;

import com.iflove.entity.Const;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;
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
     * zet增加操作
     * @param key
     * @param value  属性值
     * @param map    具体分数
     * @return
     */
    public Boolean zsAdd(String key, String value, HashMap<String, Object> map){
        try {
            template.opsForZSet().add(key, value, Double.parseDouble(map.get(key).toString()));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * zset给某个key某个属性增值操作
     * @param key
     * @param value  属性值
     * @param delta  增加值
     * @return
     */
    public Boolean zsIncr(String key, String value, Integer delta){
        try {
            template.opsForZSet().incrementScore(key, value, delta);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * zset逆向排序
     * @param key
     * @return
     */
    public Set<Object>  zsReverseRange(String key){
        Set viewNum = template.opsForZSet().reverseRange(key,0,-1);

        return viewNum;

    }

    /**
     * zscore 返回属性值
     * @param key  key值
     * @param value 属性值
     * @return
     */
    public Double zscore(String key,String value){
        Double score = template.opsForZSet().score(key, value);
        return score;
    }


}
