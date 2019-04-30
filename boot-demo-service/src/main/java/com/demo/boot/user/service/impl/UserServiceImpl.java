package com.demo.boot.user.service.impl;

import com.demo.boot.user.dao.UserDao;
import com.demo.boot.user.model.User;
import com.demo.boot.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * UserServiceImpl
 *
 * @Author ddmc
 * @Create 2019-04-17 15:13
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User getById(Long id) {
        return userDao.selectById(id);
    }

    @Override
    @Transactional(value = "userTransactionManager", rollbackFor = Exception.class)
    public User insertNewUser(User user) {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            User tempUser = new User();
            tempUser.setCreateTime(new Date());
            tempUser.setUpdateTime(new Date());
            tempUser.setAge(30);
            tempUser.setName("User_" + UUID.randomUUID().toString());
            tempUser.setDescription(user.getName());
            tempUser.setField6("not null");
            users.add(tempUser);
        }
        userDao.insertList(users);


        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        user.setAge(30);
        user.setName("User_" + UUID.randomUUID().toString());
        user.setDescription(user.getName());
        user.setField6("not null");
        userDao.insert(user);
        // 经过测试：多数据源时一定要明确设置事务管理器是哪个，否则事务失效
        // System.out.println(1 / 0);
        return user;
    }
}