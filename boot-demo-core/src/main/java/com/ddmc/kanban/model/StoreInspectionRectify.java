package com.ddmc.kanban.model;

import java.util.Date;

/**
 * @Description Automatically generated by MBG.
 * @Author ddmc
 * @Datetime @mbg.generated 2019-04-03 19:55:17
 * @Table store_inspection_rectify
*/
public class StoreInspectionRectify {
    /**
     * 自增长主键: id
     * 
     * @mbg.generated 2019-04-03 19:55:17
      */
    private Integer id;

    /**
     * 门店巡检记录ID: inspection_id
     * 
     * @mbg.generated 2019-04-03 19:55:17
      */
    private Integer inspectionId;

    /**
     * 整改项名称: rectify_name
     * 
     * @mbg.generated 2019-04-03 19:55:17
      */
    private String rectifyName;

    /**
     * 整改实施人: rectify_person
     * 
     * @mbg.generated 2019-04-03 19:55:17
      */
    private String rectifyPerson;

    /**
     * 整改期限: rectify_date
     * 
     * @mbg.generated 2019-04-03 19:55:17
      */
    private Date rectifyDate;

    /**
     * 创建时间: create_time
     * 
     * @mbg.generated 2019-04-03 19:55:17
      */
    private Date createTime;

    /**
     * 更新时间: update_time
     * 
     * @mbg.generated 2019-04-03 19:55:17
      */
    private Date updateTime;

    /**
     * 整改内容: rectify_content
     * 
     * @mbg.generated 2019-04-03 19:55:17
      */
    private String rectifyContent;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getInspectionId() {
        return inspectionId;
    }

    public void setInspectionId(Integer inspectionId) {
        this.inspectionId = inspectionId;
    }

    public String getRectifyName() {
        return rectifyName;
    }

    public void setRectifyName(String rectifyName) {
        this.rectifyName = rectifyName;
    }

    public String getRectifyPerson() {
        return rectifyPerson;
    }

    public void setRectifyPerson(String rectifyPerson) {
        this.rectifyPerson = rectifyPerson;
    }

    public Date getRectifyDate() {
        return rectifyDate;
    }

    public void setRectifyDate(Date rectifyDate) {
        this.rectifyDate = rectifyDate;
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

    public String getRectifyContent() {
        return rectifyContent;
    }

    public void setRectifyContent(String rectifyContent) {
        this.rectifyContent = rectifyContent;
    }
}