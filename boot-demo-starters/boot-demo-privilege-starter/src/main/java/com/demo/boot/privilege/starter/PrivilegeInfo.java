package com.demo.boot.privilege.starter;

import java.util.Set;

/**
 * 权限信息
 *
 * @Author wude
 * @Create 2019-04-30 13:45
 */
public class PrivilegeInfo {

    private String authItem;
    private String alias;
    private Set<String> uri;
    private String controller;
    private String method;

    public String getAuthItem() {
        return authItem;
    }

    public void setAuthItem(String authItem) {
        this.authItem = authItem;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Set<String> getUri() {
        return uri;
    }

    public void setUri(Set<String> uri) {
        this.uri = uri;
    }

    public String getController() {
        return controller;
    }

    public void setController(String controller) {
        this.controller = controller;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}