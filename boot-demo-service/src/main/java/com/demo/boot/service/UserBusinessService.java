package com.demo.boot.service;

import com.demo.boot.business.dao.BusinessDao;
import com.demo.boot.business.model.Business;
import com.demo.boot.user.dao.UserDao;
import com.demo.boot.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author dell
 * @version 1.0.0
 * @create 2018-10-10 13:43
 */
@Service
public class UserBusinessService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private BusinessDao businessDao;

    @Transactional(value = "businessTransactionManager", rollbackFor = Exception.class)
    public List<Long> mulitDB() {
        User user = new User();
        long time = System.currentTimeMillis();
//        user.setUsername(time + "");
//        user.setPassword("123456");
//        userDao.insert(user);

        Business business = new Business();
//        business.setbNo(user.getUsername());
        businessDao.insert(business);

//        System.out.println(1 / 0);
//        return Arrays.asList(user.getId(), business.getId());
        return null;
    }
}