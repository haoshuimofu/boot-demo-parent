package com.ddmc.kanban.model;

/**
 * @Description Automatically generated by MBG, please do not manually modify it.
 * @Author ddmc
 * @Datetime @mbg.generated 2019-03-19 13:02:19
 * @Table store_product_batch
*/
public class StoreProductBatchKey {
    /**
     * store_id
     * 
     * @mbg.generated 2019-03-19 13:02:19
      */
    private Integer storeId;

    /**
     * product_id
     * 
     * @mbg.generated 2019-03-19 13:02:19
      */
    private Integer productId;

    /**
     * batch_id
     * 
     * @mbg.generated 2019-03-19 13:02:19
      */
    private String batchId;

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
}