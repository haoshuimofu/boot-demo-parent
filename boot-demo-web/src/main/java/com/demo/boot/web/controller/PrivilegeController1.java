package com.demo.boot.web.controller;

import com.ddmc.privilege.starter.PrivilegeCollector;
import com.ddmc.privilege.starter.PrivilegeInfo;
import com.demo.boot.base.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * @Author ddmc
 * @Create 2019-04-30 15:27
 */
//@RestController
//@RequestMapping(value = "privilege/c")
public class PrivilegeController1 {

    private Logger logger = LoggerFactory.getLogger(PrivilegeController1.class);

    @Autowired
    private PrivilegeCollector privilegeCollector;


//    @GetMapping("collect")
    public JsonResult<List<PrivilegeInfo>> collect() {
        try {
            return JsonResult.success(privilegeCollector.get());
        } catch (ExecutionException e) {
            logger.error("获取权限出错了!", e);
        }
        return JsonResult.fail("0", "获取权限出错了!");
    }
}