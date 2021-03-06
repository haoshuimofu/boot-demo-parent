package com.demo.boot.service.impl;

import com.demo.boot.service.CommonService;
import org.springframework.stereotype.Service;

/**
 * @Author ddmc
 * @Create 2019-03-01 11:15
 */
@Service("commonServiceA")
public class CommonServiceAImpl implements CommonService {
    @Override
    public String say(String name) {
        return String.format("Hi %s, I'm A impl!", name);
    }
}