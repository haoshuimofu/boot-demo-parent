package com.ddmc.kanban.model;

import java.util.Date;

/**
 * @Description Automatically generated by MBG.
 * @Author ddmc
 * @Datetime @mbg.generated 2019-04-04 10:35:23
 * @Table tool_disinfection
*/
public class ToolDisinfection {
    /**
     * 自增长主键: id
     * 
     * @mbg.generated 2019-04-04 10:35:23
      */
    private Integer id;

    /**
     * 前置门店ID: store_id
     * 
     * @mbg.generated 2019-04-04 10:35:23
      */
    private Integer storeId;

    /**
     * 消毒日期，格式：yyyyMMdd: date
     * 
     * @mbg.generated 2019-04-04 10:35:23
      */
    private Integer date;

    /**
     * 创建时间: create_time
     * 
     * @mbg.generated 2019-04-04 10:35:23
      */
    private Date createTime;

    /**
     * 更新时间: update_time
     * 
     * @mbg.generated 2019-04-04 10:35:23
      */
    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
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