package com.ddmc.kanban.model;

/**
 * @Description Automatically generated by MBG.
 * @Author ddmc
 * @Datetime @mbg.generated 2019-03-24 17:16:38
 * @Table store_product_batch
 */
public class StoreProductBatchKey {
    /**
     * store_id
     *
     * @mbg.generated 2019-03-24 17:16:38
     */
    private Integer storeId;

    /**
     * product_id
     *
     * @mbg.generated 2019-03-24 17:16:38
     */
    private Integer productId;

    /**
     * batch_id
     *
     * @mbg.generated 2019-03-24 17:16:38
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