package com.ddmc.kanban.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description Automatically generated by MBG.
 * @Author ddmc
 * @Datetime @mbg.generated 2019-03-22 15:01:53
 * @Table store_product_batch_monitor
 */
public class StoreProductBatchMonitor {
    /**
     * 自增长主键: id
     *
     * @mbg.generated 2019-03-22 15:01:53
     */
    private Long id;

    /**
     * 类型：1、临期；2、过期: type
     *
     * @mbg.generated 2019-03-22 15:01:53
     */
    private Integer type;

    /**
     * 门店ID: store_id
     *
     * @mbg.generated 2019-03-22 15:01:53
     */
    private Integer storeId;

    /**
     * 商品ID: product_id
     *
     * @mbg.generated 2019-03-22 15:01:53
     */
    private Integer productId;

    /**
     * 批次ID: batch_id
     *
     * @mbg.generated 2019-03-22 15:01:53
     */
    private String batchId;

    /**
     * store_product_batch.update_time: batch_update_time
     *
     * @mbg.generated 2019-03-22 15:01:53
     */
    private Date batchUpdateTime;

    /**
     * 保质期天数: quality_period
     *
     * @mbg.generated 2019-03-22 15:01:53
     */
    private Integer qualityPeriod;

    /**
     * 可售期天数: sale_period
     *
     * @mbg.generated 2019-03-22 15:01:53
     */
    private Integer salePeriod;

    /**
     * 商品合计数: amount
     *
     * @mbg.generated 2019-03-22 15:01:53
     */
    private BigDecimal amount;

    /**
     * 创建时间: create_time
     *
     * @mbg.generated 2019-03-22 15:01:53
     */
    private Date createTime;

    /**
     * 更新时间: update_time
     *
     * @mbg.generated 2019-03-22 15:01:53
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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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