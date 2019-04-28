package com.demo.boot.redis;

/**
 * @Author ddmc
 * @Create 2019-04-17 18:08
 */
public class RedisClusterConfigurationProperties {

    private String clusterNodes;
    private String maxRedirects;
    private Integer commandTimeout;

    public String getClusterNodes() {
        return clusterNodes;
    }

    public void setClusterNodes(String clusterNodes) {
        this.clusterNodes = clusterNodes;
    }

    public String getMaxRedirects() {
        return maxRedirects;
    }

    public void setMaxRedirects(String maxRedirects) {
        this.maxRedirects = maxRedirects;
    }

    public Integer getCommandTimeout() {
        return commandTimeout;
    }

    public void setCommandTimeout(Integer commandTimeout) {
        this.commandTimeout = commandTimeout;
    }
}