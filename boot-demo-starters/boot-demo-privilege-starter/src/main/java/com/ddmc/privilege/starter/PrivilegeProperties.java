package com.ddmc.privilege.starter;

/**
 * Log配置
 *
 * @Author ddmc
 * @Create 2019-04-29 10:08
 */

public class PrivilegeProperties {
    private boolean init;
    private int idle = 5;

    public boolean isInit() {
        return init;
    }

    public void setInit(boolean init) {
        this.init = init;
    }

    public int getIdle() {
        return idle;
    }

    public void setIdle(int idle) {
        this.idle = idle;
    }
}