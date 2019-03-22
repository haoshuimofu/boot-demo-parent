package com.ddmc.kanban.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description Automatically generated by MBG, please do not manually modify it.
 * @Author ddmc
 * @Datetime @mbg.generated 2019-03-22 14:20:27
 * @Table product
*/
public class Product {
    /**
     * product_id
     * 
     * @mbg.generated 2019-03-22 14:20:27
      */
    private Integer productId;

    /**
     * 重量: weight
     * 
     * @mbg.generated 2019-03-22 14:20:27
      */
    private Integer weight;

    /**
     * product_name
     * 
     * @mbg.generated 2019-03-22 14:20:27
      */
    private String productName;

    /**
     * 单位: unit_name
     * 
     * @mbg.generated 2019-03-22 14:20:27
      */
    private String unitName;

    /**
     * 条码: bar_code
     * 
     * @mbg.generated 2019-03-22 14:20:27
      */
    private String barCode;

    /**
     * remark
     * 
     * @mbg.generated 2019-03-22 14:20:27
      */
    private String remark;

    /**
     * create_time
     * 
     * @mbg.generated 2019-03-22 14:20:27
      */
    private Date createTime;

    /**
     * update_time
     * 
     * @mbg.generated 2019-03-22 14:20:27
      */
    private Date updateTime;

    /**
     * 所属类目: type_id
     * 
     * @mbg.generated 2019-03-22 14:20:27
      */
    private Integer typeId;

    /**
     * status
     * 
     * @mbg.generated 2019-03-22 14:20:27
      */
    private Integer status;

    /**
     * 商品图片: image
     * 
     * @mbg.generated 2019-03-22 14:20:27
      */
    private String image;

    /**
     * 商品预估算法: arithmetic_type
     * 
     * @mbg.generated 2019-03-22 14:20:27
      */
    private Integer arithmeticType;

    /**
     * 是否原料: is_material
     * 
     * @mbg.generated 2019-03-22 14:20:27
      */
    private Integer isMaterial;

    /**
     * 箱规: unit_size
     * 
     * @mbg.generated 2019-03-22 14:20:27
      */
    private String unitSize;

    /**
     * 商品id: sku_id
     * 
     * @mbg.generated 2019-03-22 14:20:27
      */
    private String skuId;

    /**
     * 更新人: update_user
     * 
     * @mbg.generated 2019-03-22 14:20:27
      */
    private String updateUser;

    /**
     * 是否强制扫码: is_scan
     * 
     * @mbg.generated 2019-03-22 14:20:27
      */
    private Integer isScan;

    /**
     * 是否售卖: is_sale
     * 
     * @mbg.generated 2019-03-22 14:20:27
      */
    private Integer isSale;

    /**
     * 扫描盘货: is_inventory_scan
     * 
     * @mbg.generated 2019-03-22 14:20:27
      */
    private Integer isInventoryScan;

    /**
     * 内部分类: category
     * 
     * @mbg.generated 2019-03-22 14:20:27
      */
    private String category;

    /**
     * 分类路径: category_path
     * 
     * @mbg.generated 2019-03-22 14:20:27
      */
    private String categoryPath;

    /**
     * 销售规格: sizes
     * 
     * @mbg.generated 2019-03-22 14:20:27
      */
    private String sizes;

    /**
     * 净含量单位: weight_unit
     * 
     * @mbg.generated 2019-03-22 14:20:27
      */
    private String weightUnit;

    /**
     * 类目属性: propertys
     * 
     * @mbg.generated 2019-03-22 14:20:27
      */
    private String propertys;

    /**
     * 保存条件值: storage_value_id
     * 
     * @mbg.generated 2019-03-22 14:20:27
      */
    private String storageValueId;

    /**
     * 采购开关 1需采购 0否 : purchase_on
     * 
     * @mbg.generated 2019-03-22 14:20:27
      */
    private Integer purchaseOn;

    /**
     * 批次开关: is_batch
     * 
     * @mbg.generated 2019-03-22 14:20:27
      */
    private Integer isBatch;

    /**
     * 开启批次时间: is_batch_time
     * 
     * @mbg.generated 2019-03-22 14:20:27
      */
    private Date isBatchTime;

    /**
     * 调拨方式 0保留库存 1全部调拨: transfer_type
     * 
     * @mbg.generated 2019-03-22 14:20:27
      */
    private Integer transferType;

    /**
     * 采购策略: purchase_strategy
     * 
     * @mbg.generated 2019-03-22 14:20:27
      */
    private Integer purchaseStrategy;

    /**
     * 箱规: box_size
     * 
     * @mbg.generated 2019-03-22 14:20:27
      */
    private String boxSize;

    /**
     * 人工成本: labor_cost
     * 
     * @mbg.generated 2019-03-22 14:20:27
      */
    private String laborCost;

    /**
     * 进项税: in_tax
     * 
     * @mbg.generated 2019-03-22 14:20:27
      */
    private BigDecimal inTax;

    /**
     * 销项税: out_tax
     * 
     * @mbg.generated 2019-03-22 14:20:27
      */
    private BigDecimal outTax;

    /**
     * 仅限总仓生产: only_general_store
     * 
     * @mbg.generated 2019-03-22 14:20:27
      */
    private Integer onlyGeneralStore;

    /**
     * 是否自动: is_auto
     * 
     * @mbg.generated 2019-03-22 14:20:27
      */
    private Integer isAuto;

    /**
     * 调拨提前期: transfer_advance_day
     * 
     * @mbg.generated 2019-03-22 14:20:27
      */
    private Integer transferAdvanceDay;

    /**
     * 采购到址: purchase_to_address
     * 
     * @mbg.generated 2019-03-22 14:20:27
      */
    private Integer purchaseToAddress;

    /**
     * 门店存货情况: store_inventory
     * 
     * @mbg.generated 2019-03-22 14:20:27
      */
    private Integer storeInventory;

    /**
     * store_list
     * 
     * @mbg.generated 2019-03-22 14:20:27
      */
    private String storeList;

    /**
     * 自动填写批次: auto_batch
     * 
     * @mbg.generated 2019-03-22 14:20:27
      */
    private Integer autoBatch;

    /**
     * å‡€å«é‡ä¸Šä¸‹é™: weight_limit
     * 
     * @mbg.generated 2019-03-22 14:20:27
      */
    private String weightLimit;

    /**
     * ç”Ÿäº§æ–¹ 0ä¾›è´§å•†ã€1é—¨åº—ã€2æ€»ä»“: producer
     * 
     * @mbg.generated 2019-03-22 14:20:27
      */
    private Integer producer;

    /**
     * 保质期: quality_period
     * 
     * @mbg.generated 2019-03-22 14:20:27
      */
    private Integer qualityPeriod;

    /**
     * 可售期: sale_period
     * 
     * @mbg.generated 2019-03-22 14:20:27
      */
    private Integer salePeriod;

    /**
     * 批次类型 1按日 2按周 3按月: batch_type
     * 
     * @mbg.generated 2019-03-22 14:20:27
      */
    private Integer batchType;

    /**
     * 分拣成本: distribute_cost
     * 
     * @mbg.generated 2019-03-22 14:20:27
      */
    private String distributeCost;

    /**
     * 标准装筐数量: basket_num
     * 
     * @mbg.generated 2019-03-22 14:20:27
      */
    private String basketNum;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getArithmeticType() {
        return arithmeticType;
    }

    public void setArithmeticType(Integer arithmeticType) {
        this.arithmeticType = arithmeticType;
    }

    public Integer getIsMaterial() {
        return isMaterial;
    }

    public void setIsMaterial(Integer isMaterial) {
        this.isMaterial = isMaterial;
    }

    public String getUnitSize() {
        return unitSize;
    }

    public void setUnitSize(String unitSize) {
        this.unitSize = unitSize;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Integer getIsScan() {
        return isScan;
    }

    public void setIsScan(Integer isScan) {
        this.isScan = isScan;
    }

    public Integer getIsSale() {
        return isSale;
    }

    public void setIsSale(Integer isSale) {
        this.isSale = isSale;
    }

    public Integer getIsInventoryScan() {
        return isInventoryScan;
    }

    public void setIsInventoryScan(Integer isInventoryScan) {
        this.isInventoryScan = isInventoryScan;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategoryPath() {
        return categoryPath;
    }

    public void setCategoryPath(String categoryPath) {
        this.categoryPath = categoryPath;
    }

    public String getSizes() {
        return sizes;
    }

    public void setSizes(String sizes) {
        this.sizes = sizes;
    }

    public String getWeightUnit() {
        return weightUnit;
    }

    public void setWeightUnit(String weightUnit) {
        this.weightUnit = weightUnit;
    }

    public String getPropertys() {
        return propertys;
    }

    public void setPropertys(String propertys) {
        this.propertys = propertys;
    }

    public String getStorageValueId() {
        return storageValueId;
    }

    public void setStorageValueId(String storageValueId) {
        this.storageValueId = storageValueId;
    }

    public Integer getPurchaseOn() {
        return purchaseOn;
    }

    public void setPurchaseOn(Integer purchaseOn) {
        this.purchaseOn = purchaseOn;
    }

    public Integer getIsBatch() {
        return isBatch;
    }

    public void setIsBatch(Integer isBatch) {
        this.isBatch = isBatch;
    }

    public Date getIsBatchTime() {
        return isBatchTime;
    }

    public void setIsBatchTime(Date isBatchTime) {
        this.isBatchTime = isBatchTime;
    }

    public Integer getTransferType() {
        return transferType;
    }

    public void setTransferType(Integer transferType) {
        this.transferType = transferType;
    }

    public Integer getPurchaseStrategy() {
        return purchaseStrategy;
    }

    public void setPurchaseStrategy(Integer purchaseStrategy) {
        this.purchaseStrategy = purchaseStrategy;
    }

    public String getBoxSize() {
        return boxSize;
    }

    public void setBoxSize(String boxSize) {
        this.boxSize = boxSize;
    }

    public String getLaborCost() {
        return laborCost;
    }

    public void setLaborCost(String laborCost) {
        this.laborCost = laborCost;
    }

    public BigDecimal getInTax() {
        return inTax;
    }

    public void setInTax(BigDecimal inTax) {
        this.inTax = inTax;
    }

    public BigDecimal getOutTax() {
        return outTax;
    }

    public void setOutTax(BigDecimal outTax) {
        this.outTax = outTax;
    }

    public Integer getOnlyGeneralStore() {
        return onlyGeneralStore;
    }

    public void setOnlyGeneralStore(Integer onlyGeneralStore) {
        this.onlyGeneralStore = onlyGeneralStore;
    }

    public Integer getIsAuto() {
        return isAuto;
    }

    public void setIsAuto(Integer isAuto) {
        this.isAuto = isAuto;
    }

    public Integer getTransferAdvanceDay() {
        return transferAdvanceDay;
    }

    public void setTransferAdvanceDay(Integer transferAdvanceDay) {
        this.transferAdvanceDay = transferAdvanceDay;
    }

    public Integer getPurchaseToAddress() {
        return purchaseToAddress;
    }

    public void setPurchaseToAddress(Integer purchaseToAddress) {
        this.purchaseToAddress = purchaseToAddress;
    }

    public Integer getStoreInventory() {
        return storeInventory;
    }

    public void setStoreInventory(Integer storeInventory) {
        this.storeInventory = storeInventory;
    }

    public String getStoreList() {
        return storeList;
    }

    public void setStoreList(String storeList) {
        this.storeList = storeList;
    }

    public Integer getAutoBatch() {
        return autoBatch;
    }

    public void setAutoBatch(Integer autoBatch) {
        this.autoBatch = autoBatch;
    }

    public String getWeightLimit() {
        return weightLimit;
    }

    public void setWeightLimit(String weightLimit) {
        this.weightLimit = weightLimit;
    }

    public Integer getProducer() {
        return producer;
    }

    public void setProducer(Integer producer) {
        this.producer = producer;
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

    public Integer getBatchType() {
        return batchType;
    }

    public void setBatchType(Integer batchType) {
        this.batchType = batchType;
    }

    public String getDistributeCost() {
        return distributeCost;
    }

    public void setDistributeCost(String distributeCost) {
        this.distributeCost = distributeCost;
    }

    public String getBasketNum() {
        return basketNum;
    }

    public void setBasketNum(String basketNum) {
        this.basketNum = basketNum;
    }
}