package com.demo.boot.web.ctrl;

import com.demo.boot.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Hello world!
 *
 * @author dell
 * @version 1.0.0
 * @create 2018-08-04 12:15
 */
//@RestController
//@RequestMapping("/test")
public class TestingController {

    private Logger logger = LoggerFactory.getLogger(TestingController.class);

    private final IUserService iUserService;

    @Autowired
    public TestingController(IUserService iUserService) {
        this.iUserService = iUserService;
    }

    @RequestMapping("/getUser")
    public Object helloWorld() {
        logger.info("TestingController: getUserById({})", 1);
        return iUserService.selectById(1);
    }

    @RequestMapping("/addUser")
    public Object addUser() {
        logger.info("TestingController: addUser({})", 1);
        return iUserService.createUser();
    }

}