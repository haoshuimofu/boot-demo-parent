package com.demo.boot.user.model;

/**
 * @Description Automatically generated by MBG, please do not manually modify it.
 * @Author ddmc
 * @Datetime @mbg.generated 2019-03-26 15:09:58
 * @Table t_roles
*/
public class Role {
    /**
     * 自增长主键: id
     * 
     * @mbg.generated 2019-03-26 15:09:58
      */
    private Integer id;

    /**
     * role_name
     * 
     * @mbg.generated 2019-03-26 15:09:58
      */
    private String roleName;

    /**
     * role_desc
     * 
     * @mbg.generated 2019-03-26 15:09:58
      */
    private String roleDesc;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }
}