package com.demo.boot.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * Redis缓存
 *
 * @Author ddmc
 * @Create 2019-04-25 15:55
 */
@Service
public class RedisCacheManager {

    private final RedisTemplate redisTemplate;

    public RedisCacheManager(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 根据缓存Key清除缓存
     *
     * @param key
     * @return
     */
    public Boolean del(String key) {
        return redisTemplate.delete(key);
    }

    public Object get(String key) {
        return redisTemplate.opsForList().range(key, 0, redisTemplate.opsForList().size(key) - 1);
    }

    /**
     * setnx: 超时单位默认秒
     *
     * @param key     缓存Key
     * @param value   缓存Value
     * @param timeout 缓存ttl
     * @return
     */
    public boolean setnx(String key, Object value, long timeout) {
        return redisTemplate.opsForValue().setIfAbsent(key, value, timeout, TimeUnit.SECONDS);
    }

    public Long putList(String key, Object... values) {
        if (values == null || values.length == 0) {
            return 0L;
        }
        return redisTemplate.opsForList().leftPushAll(key, values);
    }

    public Object rightPop(String key) {
        return redisTemplate.opsForList().rightPop(key);
    }
}