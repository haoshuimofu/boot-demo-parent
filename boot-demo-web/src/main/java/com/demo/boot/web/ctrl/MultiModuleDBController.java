package com.demo.boot.web.ctrl;

import com.demo.boot.business.dao.BusinessDao;
import com.demo.boot.business.model.Business;
import com.demo.boot.service.UserBusinessService;
import com.demo.boot.user.dao.UserDao;
import com.demo.boot.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dell
 * @version 1.0.0
 * @create 2018-09-30 16:03
 */
@RestController
@RequestMapping(value = "multi")
public class MultiModuleDBController {
    @Autowired
    private UserDao userDao;
    @Autowired
    private BusinessDao businessDao;
    @Autowired
    private UserBusinessService userBusinessService;

    @GetMapping(value = "/test")
    public List test() {
        List<String> datas = new ArrayList<>();
        User user = userDao.selectById(1);
        if (user != null) {
//            datas.add(user.getUsername());
        }
        Business business = businessDao.selectById(1L);
        if (business != null) {
            datas.add(business.getbNo());
        }
        return datas;
    }

    @GetMapping(value = "/shiwu")
    public List testMultiDbShiwu() {

        return userBusinessService.mulitDB();

    }


}