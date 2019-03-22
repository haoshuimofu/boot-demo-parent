package com.ddmc.kanban.model;

import java.util.Date;

/**
 * @Description Automatically generated by MBG, please do not manually modify it.
 * @Author ddmc
 * @Datetime @mbg.generated 2019-03-22 14:20:27
 * @Table product_monitor
*/
public class ProductMonitor {
    /**
     * 自增长主键: id
     * 
     * @mbg.generated 2019-03-22 14:20:27
      */
    private Long id;

    /**
     * 1、临期商品，2、过期商品: type
     * 
     * @mbg.generated 2019-03-22 14:20:27
      */
    private Integer type;

    /**
     * 统计了多少样商品？: sku_sum
     * 
     * @mbg.generated 2019-03-22 14:20:27
      */
    private Integer skuSum;

    /**
     * 临期或过期商品总数: total
     * 
     * @mbg.generated 2019-03-22 14:20:27
      */
    private Integer total;

    /**
     * 执行开始时间: exe_start_time
     * 
     * @mbg.generated 2019-03-22 14:20:27
      */
    private Date exeStartTime;

    /**
     * 执行结束时间: exe_end_time
     * 
     * @mbg.generated 2019-03-22 14:20:27
      */
    private Date exeEndTime;

    /**
     * 统计时间，格式yyyymmddhh: count_time
     * 
     * @mbg.generated 2019-03-22 14:20:27
      */
    private Integer countTime;

    /**
     * 创建时间: create_time
     * 
     * @mbg.generated 2019-03-22 14:20:27
      */
    private Date createTime;

    /**
     * 更新时间: update_time
     * 
     * @mbg.generated 2019-03-22 14:20:27
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

    public Integer getSkuSum() {
        return skuSum;
    }

    public void setSkuSum(Integer skuSum) {
        this.skuSum = skuSum;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Date getExeStartTime() {
        return exeStartTime;
    }

    public void setExeStartTime(Date exeStartTime) {
        this.exeStartTime = exeStartTime;
    }

    public Date getExeEndTime() {
        return exeEndTime;
    }

    public void setExeEndTime(Date exeEndTime) {
        this.exeEndTime = exeEndTime;
    }

    public Integer getCountTime() {
        return countTime;
    }

    public void setCountTime(Integer countTime) {
        this.countTime = countTime;
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