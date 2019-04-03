package com.ddmc.kanban.model;

import java.util.Date;

/**
 * @Description Automatically generated by MBG.
 * @Author ddmc
 * @Datetime @mbg.generated 2019-04-03 16:35:56
 * @Table store_inspection
*/
public class StoreInspection {
    /**
     * 自增长主键: id
     * 
     * @mbg.generated 2019-04-03 16:35:56
      */
    private Integer id;

    /**
     * 前置门店ID: store_id
     * 
     * @mbg.generated 2019-04-03 16:35:56
      */
    private Integer storeId;

    /**
     * 巡检日期，格式：yyyyMMdd: date
     * 
     * @mbg.generated 2019-04-03 16:35:56
      */
    private Integer date;

    /**
     * 巡检总分: score
     * 
     * @mbg.generated 2019-04-03 16:35:56
      */
    private Integer score;

    /**
     * 0、待发布，1、已发布: status
     * 
     * @mbg.generated 2019-04-03 16:35:56
      */
    private Integer status;

    /**
     * 巡检文件路径: file_path
     * 
     * @mbg.generated 2019-04-03 16:35:56
      */
    private String filePath;

    /**
     * 巡检人: inspector
     * 
     * @mbg.generated 2019-04-03 16:35:56
      */
    private String inspector;

    /**
     * 创建时间: create_itme
     * 
     * @mbg.generated 2019-04-03 16:35:56
      */
    private Date createItme;

    /**
     * 更新时间: update_time
     * 
     * @mbg.generated 2019-04-03 16:35:56
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

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getInspector() {
        return inspector;
    }

    public void setInspector(String inspector) {
        this.inspector = inspector;
    }

    public Date getCreateItme() {
        return createItme;
    }

    public void setCreateItme(Date createItme) {
        this.createItme = createItme;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}