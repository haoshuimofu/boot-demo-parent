package com.ddmc.kanban.model;

/**
 * @Description Automatically generated by MBG.
 * @Author ddmc
 * @Datetime @mbg.generated 2019-04-09 15:49:24
 * @Table store_product_batch
*/
public class StoreProductBatchKey {
    /**
     * store_id
     * 
     * @mbg.generated 2019-04-09 15:49:24
      */
    private Integer storeId;

    /**
     * product_id
     * 
     * @mbg.generated 2019-04-09 15:49:24
      */
    private Integer productId;

    /**
     * batch_id
     * 
     * @mbg.generated 2019-04-09 15:49:24
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