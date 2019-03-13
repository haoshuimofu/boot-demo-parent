package com.demo.boot.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class CommonServiceTest extends BootDemoApplicationTest {

    @Autowired
    @Qualifier("commonServiceA")
    private CommonService commonService;


    @Test
    public void say() {
        System.out.println(commonService.say("wude"));

    }


}