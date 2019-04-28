package com.demo.boot.user.service;

import com.demo.boot.user.model.User;

/**
 * UserService
 *
 * @Author ddmc
 * @Create 2019-04-17 15:12
 */
public interface UserService {

    User getById(Long id);

    User insertNewUser(User user);
}