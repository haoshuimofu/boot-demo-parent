package com.demo.boot.web.controller;

import com.demo.boot.BootDemoWebApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.JedisCluster;

/**
 * @Author ddmc
 * @Create 2019-04-17 19:14
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {BootDemoWebApplication.class, RedisClusterController.class})
public class RedisClusterController {

    @Autowired
    private JedisCluster jedisCluster;

    @Test
    public void testSetGet() {
        jedisCluster.set("name", "wude");
        System.out.println(jedisCluster.get("name"));
    }
}