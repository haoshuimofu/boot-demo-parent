package com.ddmc.kanban.model;

import java.util.Date;

/**
 * @Description Automatically generated by MBG.
 * @Author ddmc
 * @Datetime @mbg.generated 2019-04-04 14:06:56
 * @Table pest_inspection_mouse_dict
*/
public class PestInspectionMouseDict {
    /**
     * 自增长主键: id
     * 
     * @mbg.generated 2019-04-04 14:06:56
      */
    private Integer id;

    /**
     * 粘鼠板名称: name
     * 
     * @mbg.generated 2019-04-04 14:06:56
      */
    private String name;

    /**
     * 是否删除：0否1是: is_delete
     * 
     * @mbg.generated 2019-04-04 14:06:56
      */
    private Integer isDelete;

    /**
     * 创建时间: create_time
     * 
     * @mbg.generated 2019-04-04 14:06:56
      */
    private Date createTime;

    /**
     * 更新时间: update_time
     * 
     * @mbg.generated 2019-04-04 14:06:56
      */
    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}