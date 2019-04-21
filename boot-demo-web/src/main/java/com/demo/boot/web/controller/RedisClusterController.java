package com.demo.boot.web.controller;

import com.demo.boot.BootDemoWebApplication;
import com.demo.boot.redis.RedisClusterConfigurationProperties;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.JedisCluster;

/**
 * @Author ddmc
 * @Create 2019-04-17 19:14
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {BootDemoWebApplication.class, RedisClusterController.class})
public class RedisClusterController {

//    @Autowired
//    private JedisCluster jedisCluster;
//    @Autowired
//    RedisClusterConfigurationProperties redisClusterConfigurationProperties;

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testSetGet() {
//        String[] serverArray = redisClusterConfigurationProperties.getClusterNodes().split(",");
//        Set<HostAndPort> nodes = new HashSet<>();
//
//        for (String ipPort : serverArray) {
//            String[] ipPortPair = ipPort.split(":");
//            nodes.add(new HostAndPort(ipPortPair[0].trim(), Integer.valueOf(ipPortPair[1].trim())));
//        }
//        JedisCluster jedisCluster = new JedisCluster(nodes, redisClusterConfigurationProperties.getCommandTimeout());
//        jedisCluster.set("name", "wude");
//        System.out.println(jedisCluster.get("name"));
        redisTemplate.opsForValue().set("name", "wude");
        System.out.println(redisTemplate.opsForValue().get("name"));


    }
}