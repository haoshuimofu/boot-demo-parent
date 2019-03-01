package com.demo.boot.web.ctrl;

import com.demo.boot.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author ddmc
 * @Create 2019-03-01 11:17
 */
@RestController
@RequestMapping(value = "common/service")
public class CommonServiceTestController {

//    @Autowired
//    @Qualifier("commonServiceA")
    @Resource(name = "commonServiceA")
    private CommonService commonService;

    @RequestMapping("test")
    public String commonServiceTest() {
        return commonService.say("test");
    }



}