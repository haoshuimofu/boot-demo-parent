package com.ddmc.privilege.starter;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Log配置
 *
 * @Author ddmc
 * @Create 2019-04-29 10:08
 */

@ConfigurationProperties(prefix = "privilege.collect")
public class PrivilegeConfigProperties {

    private boolean enable;

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}