package com.ddmc.kanban.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description Automatically generated by MBG, please do not manually modify it.
 * @Author ddmc
 * @Datetime @mbg.generated 2019-03-24 17:16:38
 * @Table store_product_batch
*/
public class StoreProductBatch extends StoreProductBatchKey {
    /**
     * amount
     * 
     * @mbg.generated 2019-03-24 17:16:38
      */
    private BigDecimal amount;

    /**
     * create_time
     * 
     * @mbg.generated 2019-03-24 17:16:38
      */
    private Date createTime;

    /**
     * update_time
     * 
     * @mbg.generated 2019-03-24 17:16:38
      */
    private Date updateTime;

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