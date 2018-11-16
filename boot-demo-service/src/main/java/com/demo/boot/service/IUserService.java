package com.demo.boot.service;

import com.demo.boot.model.User;

/**
 * @author dell
 * @version 1.0.0
 * @create 2018-08-16 16:14
 */
public interface IUserService {
    User selectById(Integer integer);

    Long createUser();
}