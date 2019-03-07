package com.demo.boot.web.command;

import com.demo.boot.base.validator.IsMobile;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * 校验器测试Command
 *
 * @Author ddmc
 * @Create 2019-02-28 16:39
 */
public class ValidatorTestCommand {
    @NotEmpty(message = "不能为空")
    @IsMobile
    private String mobile;

    @NotEmpty(message = "不能为空")
    private String name;
    @NotNull(message = "不能为空")
    @Min(value = 1, message = "最小值%s")
    private Integer age;
//    @NotEmpty(message = "不能为空")
    @Size(min = 1, max = 4, message = "list长度错误")
    private List<String> images;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

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

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}