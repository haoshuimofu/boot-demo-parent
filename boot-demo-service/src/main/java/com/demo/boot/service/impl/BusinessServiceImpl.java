package com.demo.boot.service.impl;

import com.demo.boot.business.dao.BusinessDao;
import com.demo.boot.business.model.Business;
import com.demo.boot.service.IBusinessService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author dell
 * @version 1.0.0
 * @create 2018-10-10 13:38
 */
public class BusinessServiceImpl implements IBusinessService {

    @Autowired
    private BusinessDao businessDao;

    public int insert(Business business) {
        return businessDao.insert(business);
    }
}