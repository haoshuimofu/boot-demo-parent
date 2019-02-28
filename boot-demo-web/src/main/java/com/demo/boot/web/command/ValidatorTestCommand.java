package com.demo.boot.web.command;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

/**
 * 校验器测试Command
 *
 * @Author ddmc
 * @Create 2019-02-28 16:39
 */
public class ValidatorTestCommand {
    @NotEmpty(message = "name不能为空!")
    private String name;
    @Min(value = 1, message = "年龄最小值错误")
    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}