package com.demo.boot.redis;

/**
 * @Author ddmc
 * @Create 2019-04-17 18:08
 */
public class RedisClusterConfigurationProperties {

    private String clusterNodes;
    private Integer commandTimeout;

    public String getClusterNodes() {
        return clusterNodes;
    }

    public void setClusterNodes(String clusterNodes) {
        this.clusterNodes = clusterNodes;
    }

    public Integer getCommandTimeout() {
        return commandTimeout;
    }

    public void setCommandTimeout(Integer commandTimeout) {
        this.commandTimeout = commandTimeout;
    }
}