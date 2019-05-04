package com.ddmc.privilege.starter;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Log配置
 *
 * @Author ddmc
 * @Create 2019-04-29 10:08
 */

@ConfigurationProperties(prefix = "privilege.collect")
public class PrivilegeProperties {

    private int idle = 5;

    public int getIdle() {
        return idle;
    }

    public void setIdle(int idle) {
        this.idle = idle;
    }
}