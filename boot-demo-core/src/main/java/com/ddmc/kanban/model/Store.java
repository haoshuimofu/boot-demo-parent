package com.ddmc.kanban.model;

import java.util.Date;

/**
 * @Description Automatically generated by MBG, please do not manually modify it.
 * @Author ddmc
 * @Datetime @mbg.generated 2019-03-22 13:50:15
 * @Table store
*/
public class Store {
    /**
     * store_id
     * 
     * @mbg.generated 2019-03-22 13:50:15
      */
    private Integer storeId;

    /**
     * store_name
     * 
     * @mbg.generated 2019-03-22 13:50:15
      */
    private String storeName;

    /**
     * 城市id: city_id
     * 
     * @mbg.generated 2019-03-22 13:50:15
      */
    private String cityId;

    /**
     * address
     * 
     * @mbg.generated 2019-03-22 13:50:15
      */
    private String address;

    /**
     * phone
     * 
     * @mbg.generated 2019-03-22 13:50:15
      */
    private String phone;

    /**
     * create_time
     * 
     * @mbg.generated 2019-03-22 13:50:15
      */
    private Date createTime;

    /**
     * update_time
     * 
     * @mbg.generated 2019-03-22 13:50:15
      */
    private Date updateTime;

    /**
     * status
     * 
     * @mbg.generated 2019-03-22 13:50:15
      */
    private Integer status;

    /**
     * 是否参加采购计划, 0:否,1:是: is_purchase
     * 
     * @mbg.generated 2019-03-22 13:50:15
      */
    private Integer isPurchase;

    /**
     * is_general
     * 
     * @mbg.generated 2019-03-22 13:50:15
      */
    private Integer isGeneral;

    /**
     * 更新人: update_user
     * 
     * @mbg.generated 2019-03-22 13:50:15
      */
    private String updateUser;

    /**
     * 服务站ID: station_id
     * 
     * @mbg.generated 2019-03-22 13:50:15
      */
    private String stationId;

    /**
     * 排序值 : sort
     * 
     * @mbg.generated 2019-03-22 13:50:15
      */
    private Float sort;

    /**
     * 是否测试仓: is_test
     * 
     * @mbg.generated 2019-03-22 13:50:15
      */
    private Integer isTest;

    /**
     * 是否前置仓: is_prestore
     * 
     * @mbg.generated 2019-03-22 13:50:15
      */
    private Integer isPrestore;

    /**
     * 是否售卖仓 0否 1是: is_sale
     * 
     * @mbg.generated 2019-03-22 13:50:15
      */
    private Integer isSale;

    /**
     * 是否强扫: is_scan
     * 
     * @mbg.generated 2019-03-22 13:50:15
      */
    private Integer isScan;

    /**
     * 仓库类型:1总仓存储2临时存储3销售4测试5报废存储: type
     * 
     * @mbg.generated 2019-03-22 13:50:15
      */
    private Integer type;

    /**
     * 是否开启香葱提醒:0否1是: is_chive_notice
     * 
     * @mbg.generated 2019-03-22 13:50:15
      */
    private Boolean isChiveNotice;

    /**
     * remark
     * 
     * @mbg.generated 2019-03-22 13:50:15
      */
    private String remark;

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsPurchase() {
        return isPurchase;
    }

    public void setIsPurchase(Integer isPurchase) {
        this.isPurchase = isPurchase;
    }

    public Integer getIsGeneral() {
        return isGeneral;
    }

    public void setIsGeneral(Integer isGeneral) {
        this.isGeneral = isGeneral;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public Float getSort() {
        return sort;
    }

    public void setSort(Float sort) {
        this.sort = sort;
    }

    public Integer getIsTest() {
        return isTest;
    }

    public void setIsTest(Integer isTest) {
        this.isTest = isTest;
    }

    public Integer getIsPrestore() {
        return isPrestore;
    }

    public void setIsPrestore(Integer isPrestore) {
        this.isPrestore = isPrestore;
    }

    public Integer getIsSale() {
        return isSale;
    }

    public void setIsSale(Integer isSale) {
        this.isSale = isSale;
    }

    public Integer getIsScan() {
        return isScan;
    }

    public void setIsScan(Integer isScan) {
        this.isScan = isScan;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Boolean getIsChiveNotice() {
        return isChiveNotice;
    }

    public void setIsChiveNotice(Boolean isChiveNotice) {
        this.isChiveNotice = isChiveNotice;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}