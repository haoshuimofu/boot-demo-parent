package com.demo.boot.web.controller;

import com.demo.boot.privilege.starter.Privilege;
import com.demo.boot.user.model.User;
import com.demo.boot.user.service.UserService;
import com.demo.starter.log.Log;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * User
 *
 * @Author ddmc
 * @Create 2019-04-17 15:14
 */
@RestController
@RequestMapping(value = "user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Log
    @Privilege(authItem = "user.getUser", name = "获取用户详情")
    @GetMapping(value = "get/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.getById(id);
    }

    @GetMapping(value = "insert")
    public User insertUser() {
        return userService.insertNewUser(new User());
    }

}