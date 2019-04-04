package com.ddmc.kanban.model;

import java.util.Date;

/**
 * @Description Automatically generated by MBG.
 * @Author ddmc
 * @Datetime @mbg.generated 2019-04-04 11:42:07
 * @Table recycle_area_inspection
*/
public class RecycleAreaInspection {
    /**
     * 自增长主键: id
     * 
     * @mbg.generated 2019-04-04 11:42:07
      */
    private Integer id;

    /**
     * 前置门店ID: store_id
     * 
     * @mbg.generated 2019-04-04 11:42:07
      */
    private Integer storeId;

    /**
     * 巡检日期，格式：yyyyMMdd: date
     * 
     * @mbg.generated 2019-04-04 11:42:07
      */
    private Integer date;

    /**
     * 巡检人: inspector
     * 
     * @mbg.generated 2019-04-04 11:42:07
      */
    private String inspector;

    /**
     * 回收区图片地址: recycle_area_img
     * 
     * @mbg.generated 2019-04-04 11:42:07
      */
    private String recycleAreaImg;

    /**
     * 报废区图片地址: scrap_area_img
     * 
     * @mbg.generated 2019-04-04 11:42:07
      */
    private String scrapAreaImg;

    /**
     * 处理区图片地址: handle_area_img
     * 
     * @mbg.generated 2019-04-04 11:42:07
      */
    private String handleAreaImg;

    /**
     * 完成状态 1-未完成，2-完成: status
     * 
     * @mbg.generated 2019-04-04 11:42:07
      */
    private Integer status;

    /**
     * 完成时间: finish_time
     * 
     * @mbg.generated 2019-04-04 11:42:07
      */
    private Date finishTime;

    /**
     * 创建时间: create_time
     * 
     * @mbg.generated 2019-04-04 11:42:07
      */
    private Date createTime;

    /**
     * 更新时间: update_time
     * 
     * @mbg.generated 2019-04-04 11:42:07
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

    public String getInspector() {
        return inspector;
    }

    public void setInspector(String inspector) {
        this.inspector = inspector;
    }

    public String getRecycleAreaImg() {
        return recycleAreaImg;
    }

    public void setRecycleAreaImg(String recycleAreaImg) {
        this.recycleAreaImg = recycleAreaImg;
    }

    public String getScrapAreaImg() {
        return scrapAreaImg;
    }

    public void setScrapAreaImg(String scrapAreaImg) {
        this.scrapAreaImg = scrapAreaImg;
    }

    public String getHandleAreaImg() {
        return handleAreaImg;
    }

    public void setHandleAreaImg(String handleAreaImg) {
        this.handleAreaImg = handleAreaImg;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
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