package com.demo.boot.service.impl;

import com.demo.boot.dao.UserDao;
import com.demo.boot.model.User;
import com.demo.boot.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author dell
 * @version 1.0.0
 * @create 2018-08-16 16:14
 */
//@Service
//@Primary
public class UserServiceImpl implements IUserService {

    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User selectById(Integer integer) {
        return userDao.selectById(integer);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createUser() {
        User user = new User();
        user.setUsername("wude");
        user.setPassword("123456");
        userDao.insert(user);
        return user.getId();
    }
}