package com.demo.boot.web.ctrl;

import com.demo.boot.base.JsonResult;
import com.demo.boot.web.command.ValidatorTestCommand;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 校验器测试
 *
 * @Author ddmc
 * @Create 2019-02-28 16:38
 */
@RestController
@RequestMapping("validator")
public class ValidatorTestController {

    @RequestMapping("test")
    public Object test(@RequestBody @Valid ValidatorTestCommand command) {
        return JsonResult.success();
    }

}