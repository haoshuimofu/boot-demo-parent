package com.demo.boot.user.model;

/**
 * @Description Automatically generated by MBG.
 * @Author ddmc
 * @Datetime @mbg.generated 2019-03-26 15:09:58
 * @Table t_user_role
 */
public class UserRoleKey {
    /**
     * 户用ID: user_id
     *
     * @mbg.generated 2019-03-26 15:09:58
     */
    private Integer userId;

    /**
     * 角色ID: role_id
     *
     * @mbg.generated 2019-03-26 15:09:58
     */
    private Integer roleId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}