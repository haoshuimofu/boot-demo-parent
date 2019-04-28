package com.demo.boot.web.controller;

import com.demo.boot.BootDemoWebApplication;
import com.demo.boot.redis.RedisCacheManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @Author ddmc
 * @Create 2019-04-17 19:14
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {BootDemoWebApplication.class, RedisClusterController.class})
public class RedisClusterController {

    Logger logger = LoggerFactory.getLogger(RedisClusterController.class);


    @Autowired
    private RedisCacheManager redisCacheManager;

    @Test
    public void testSetGet() {
        boolean result = redisCacheManager.setnx("ip", "10.172.73.63", 10);
        logger.info("put first result: {}", result);
        result = redisCacheManager.setnx("ip", "10.172.73.63", 10);
        logger.info("put second result: {}", result);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        result = redisCacheManager.setnx("ip", "10.172.73.63", 10);
        logger.info("put third result: {}", result);
    }

    @Test
    public void testList() {
        redisCacheManager.del("list");
        Long result = redisCacheManager.putList("list", new Integer[]{1, 2, 3, 4, 5});
        ((List<Integer>) redisCacheManager.get("list")).forEach(System.out::print);
        redisCacheManager.rightPop("list");
        System.out.println();
        ((List<Integer>) redisCacheManager.get("list")).forEach(System.out::print);
        System.out.println();
    }
}