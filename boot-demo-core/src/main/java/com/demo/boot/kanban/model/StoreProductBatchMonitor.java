package com.demo.boot.kanban.model;

import java.util.Date;

/**
 * @Description Automatically generated by MBG, please do not manually modify it.
 * @Author ddmc
 * @Datetime @mbg.generated 2019-03-18 18:39:28
 * @Table store_product_batch_monitor
*/
public class StoreProductBatchMonitor {
    /**
     * 自增长主键: id
     * 
     * @mbg.generated 2019-03-18 18:39:28
      */
    private Long id;

    /**
     * 类型：1、临期；2、过期: type
     * 
     * @mbg.generated 2019-03-18 18:39:28
      */
    private Integer type;

    /**
     * 门店ID: store_id
     * 
     * @mbg.generated 2019-03-18 18:39:28
      */
    private Integer storeId;

    /**
     * 商品ID: product_id
     * 
     * @mbg.generated 2019-03-18 18:39:28
      */
    private Integer productId;

    /**
     * 批次ID: batch_id
     * 
     * @mbg.generated 2019-03-18 18:39:28
      */
    private String batchId;

    /**
     * store_product_batch.update_time: batch_update_time
     * 
     * @mbg.generated 2019-03-18 18:39:28
      */
    private Date batchUpdateTime;

    /**
     * 商品合计数: total
     * 
     * @mbg.generated 2019-03-18 18:39:28
      */
    private Integer total;

    /**
     * 创建时间: create_time
     * 
     * @mbg.generated 2019-03-18 18:39:28
      */
    private Date createTime;

    /**
     * 更新时间: update_time
     * 
     * @mbg.generated 2019-03-18 18:39:28
      */
    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public Date getBatchUpdateTime() {
        return batchUpdateTime;
    }

    public void setBatchUpdateTime(Date batchUpdateTime) {
        this.batchUpdateTime = batchUpdateTime;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
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