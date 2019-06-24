package com.demo.boot.privilege.starter;

import com.demo.boot.base.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * @Author ddmc
 * @Create 2019-04-30 15:27
 */
@ConditionalOnBean(PrivilegeCollector.class)
@RestController
@RequestMapping(value = "privilege")
public class PrivilegeController {

    private Logger logger = LoggerFactory.getLogger(PrivilegeController.class);

    private final PrivilegeCollector privilegeCollector;

    public PrivilegeController(PrivilegeCollector privilegeCollector) {
        this.privilegeCollector = privilegeCollector;
    }


    @RequestMapping("collect")
    public JsonResult<List<PrivilegeInfo>> collect() {
        try {
            return JsonResult.success(privilegeCollector.get());
        } catch (ExecutionException e) {
            logger.error("获取权限出错了!", e);
        }
        return JsonResult.fail("0", "获取权限出错了!");
    }
}