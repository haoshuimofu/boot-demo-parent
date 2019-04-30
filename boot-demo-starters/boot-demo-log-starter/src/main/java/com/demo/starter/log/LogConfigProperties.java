package com.demo.starter.log;

import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;

/**
 * Log配置
 *
 * @Author ddmc
 * @Create 2019-04-29 10:08
 */

@ConfigurationProperties(prefix = "log")
public class LogConfigProperties {

    private String exclude;

    private String[] excludeArr;

    @PostConstruct
    public void init() {
        this.excludeArr = exclude == null ? new String[]{} : exclude.trim().split(",");
    }

    public String getExclude() {
        return exclude;
    }

    public void setExclude(String exclude) {
        this.exclude = exclude;
    }

    public String[] getExcludeArr() {
        return excludeArr;
    }
}