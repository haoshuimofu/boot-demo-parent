package com.demo.boot.common.service;

import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;

/**
 * @Author ddmc
 * @Create 2019-04-17 19:13
 */
@Service
public class RedisClusterService {
    private final JedisCluster jedisCluster;

    public RedisClusterService(JedisCluster jedisCluster) {
        this.jedisCluster = jedisCluster;
    }
}