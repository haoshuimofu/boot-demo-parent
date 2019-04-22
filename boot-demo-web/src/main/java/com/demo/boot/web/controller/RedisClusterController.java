package com.demo.boot.web.controller;

import com.demo.boot.BootDemoWebApplication;
import com.demo.boot.user.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Author ddmc
 * @Create 2019-04-17 19:14
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {BootDemoWebApplication.class, RedisClusterController.class})
public class RedisClusterController {


    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testSetGet() {
        User user = new User();
        user.setId(1L);
        user.setName("Wude");

        redisTemplate.opsForValue().set("name", user);
        System.out.println(redisTemplate.opsForValue().get("name"));


    }
}