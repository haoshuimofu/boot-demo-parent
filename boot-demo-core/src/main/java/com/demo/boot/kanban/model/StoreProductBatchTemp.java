package com.demo.boot.kanban.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description Automatically generated by MBG, please do not manually modify it.
 * @Author ddmc
 * @Datetime @mbg.generated 2019-03-18 19:46:43
 * @Table store_product_batch_temp
*/
public class StoreProductBatchTemp {
    /**
     * 自增长主键: id
     * 
     * @mbg.generated 2019-03-18 19:46:43
      */
    private Long id;

    /**
     * 门店ID: store_id
     * 
     * @mbg.generated 2019-03-18 19:46:43
      */
    private Integer storeId;

    /**
     * 商品ID: product_id
     * 
     * @mbg.generated 2019-03-18 19:46:43
      */
    private Integer productId;

    /**
     * 批次ID: batch_id
     * 
     * @mbg.generated 2019-03-18 19:46:43
      */
    private String batchId;

    /**
     * 批次库存: amount
     * 
     * @mbg.generated 2019-03-18 19:46:43
      */
    private BigDecimal amount;

    /**
     * 可售期: quality_period
     * 
     * @mbg.generated 2019-03-18 19:46:43
      */
    private Integer qualityPeriod;

    /**
     * 保质期: sale_period
     * 
     * @mbg.generated 2019-03-18 19:46:43
      */
    private Integer salePeriod;

    /**
     * 创建时间: create_time
     * 
     * @mbg.generated 2019-03-18 19:46:43
      */
    private Date createTime;

    /**
     * 更新时间: update_time
     * 
     * @mbg.generated 2019-03-18 19:46:43
      */
    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getQualityPeriod() {
        return qualityPeriod;
    }

    public void setQualityPeriod(Integer qualityPeriod) {
        this.qualityPeriod = qualityPeriod;
    }

    public Integer getSalePeriod() {
        return salePeriod;
    }

    public void setSalePeriod(Integer salePeriod) {
        this.salePeriod = salePeriod;
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