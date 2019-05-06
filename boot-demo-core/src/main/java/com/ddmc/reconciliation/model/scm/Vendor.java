package com.ddmc.reconciliation.model.scm;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description Automatically generated by MBG.
 * @Author ddmc
 * @Datetime @mbg.generated 2019-04-28 15:27:25
 * @Table vendor
*/
public class Vendor {
    /**
     * vendor_id
     * 
     * @mbg.generated 2019-04-28 15:27:25
      */
    private Integer vendorId;

    /**
     * vendor_name
     * 
     * @mbg.generated 2019-04-28 15:27:25
      */
    private String vendorName;

    /**
     * address
     * 
     * @mbg.generated 2019-04-28 15:27:25
      */
    private String address;

    /**
     * phone
     * 
     * @mbg.generated 2019-04-28 15:27:25
      */
    private String phone;

    /**
     * create_time
     * 
     * @mbg.generated 2019-04-28 15:27:25
      */
    private Date createTime;

    /**
     * update_time
     * 
     * @mbg.generated 2019-04-28 15:27:25
      */
    private Date updateTime;

    /**
     * status
     * 
     * @mbg.generated 2019-04-28 15:27:25
      */
    private Integer status;

    /**
     * 补货频次: replenish_rate
     * 
     * @mbg.generated 2019-04-28 15:27:25
      */
    private BigDecimal replenishRate;

    /**
     * 类型 0普通 1补货: type
     * 
     * @mbg.generated 2019-04-28 15:27:25
      */
    private Integer type;

    /**
     * 更新人: update_user
     * 
     * @mbg.generated 2019-04-28 15:27:25
      */
    private String updateUser;

    /**
     * 开户行: bank_name
     * 
     * @mbg.generated 2019-04-28 15:27:25
      */
    private String bankName;

    /**
     * 银行账号: bank_account
     * 
     * @mbg.generated 2019-04-28 15:27:25
      */
    private String bankAccount;

    /**
     * 开户姓名: account_name
     * 
     * @mbg.generated 2019-04-28 15:27:25
      */
    private String accountName;

    /**
     * 账户是否使用 0未使用 1使用: is_account_active
     * 
     * @mbg.generated 2019-04-28 15:27:25
      */
    private Integer isAccountActive;

    /**
     * 纳税人账号: tax_account
     * 
     * @mbg.generated 2019-04-28 15:27:25
      */
    private String taxAccount;

    /**
     * 账期: account_rate
     * 
     * @mbg.generated 2019-04-28 15:27:25
      */
    private BigDecimal accountRate;

    /**
     * 供应商性质: vendor_type
     * 
     * @mbg.generated 2019-04-28 15:27:25
      */
    private Integer vendorType;

    /**
     * 联系人: contact_user
     * 
     * @mbg.generated 2019-04-28 15:27:25
      */
    private String contactUser;

    /**
     * 品控状态: audit_status
     * 
     * @mbg.generated 2019-04-28 15:27:25
      */
    private Integer auditStatus;

    /**
     * 品控处理时间: audit_update_time
     * 
     * @mbg.generated 2019-04-28 15:27:25
      */
    private Date auditUpdateTime;

    /**
     * 品控处理人: audit_update_user
     * 
     * @mbg.generated 2019-04-28 15:27:25
      */
    private String auditUpdateUser;

    /**
     * 品控备注: audit_remark
     * 
     * @mbg.generated 2019-04-28 15:27:25
      */
    private String auditRemark;

    /**
     * 账期: account_period_type
     * 
     * @mbg.generated 2019-04-28 15:27:25
      */
    private Integer accountPeriodType;

    /**
     * 是否补货供应商: is_replenishment
     * 
     * @mbg.generated 2019-04-28 15:27:25
      */
    private Integer isReplenishment;

    /**
     * 起订金额: min_order_amount
     * 
     * @mbg.generated 2019-04-28 15:27:25
      */
    private BigDecimal minOrderAmount;

    /**
     * 门店采购: is_store_purchase
     * 
     * @mbg.generated 2019-04-28 15:27:25
      */
    private Integer isStorePurchase;

    /**
     * 配送耗时: delivery_day
     * 
     * @mbg.generated 2019-04-28 15:27:25
      */
    private Integer deliveryDay;

    /**
     * 用户名: username
     * 
     * @mbg.generated 2019-04-28 15:27:25
      */
    private String username;

    /**
     * 密码: password
     * 
     * @mbg.generated 2019-04-28 15:27:25
      */
    private String password;

    /**
     * 是否质检: is_quality
     * 
     * @mbg.generated 2019-04-28 15:27:25
      */
    private Integer isQuality;

    /**
     * 送货密码: delivery_password
     * 
     * @mbg.generated 2019-04-28 15:27:25
      */
    private String deliveryPassword;

    /**
     * 城市id: city_id
     * 
     * @mbg.generated 2019-04-28 15:27:25
      */
    private String cityId;

    /**
     * 简称: short_name
     * 
     * @mbg.generated 2019-04-28 15:27:25
      */
    private String shortName;

    /**
     * 是否自采:0否 1是: is_self_purchase
     * 
     * @mbg.generated 2019-04-28 15:27:25
      */
    private Integer isSelfPurchase;

    /**
     * 最晚到货时间00:00到24:00: arrival_deadline
     * 
     * @mbg.generated 2019-04-28 15:27:25
      */
    private String arrivalDeadline;

    /**
     * 是否需要vms系统0否1是: need_vms
     * 
     * @mbg.generated 2019-04-28 15:27:25
      */
    private Boolean needVms;

    /**
     * 可提供商品分类 1：物料耗材 2：非物料耗材, 多个用英文逗号分隔，如：1,2 : supply_product_type
     * 
     * @mbg.generated 2019-04-28 15:27:25
      */
    private String supplyProductType;

    public Integer getVendorId() {
        return vendorId;
    }

    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
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

    public BigDecimal getReplenishRate() {
        return replenishRate;
    }

    public void setReplenishRate(BigDecimal replenishRate) {
        this.replenishRate = replenishRate;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public Integer getIsAccountActive() {
        return isAccountActive;
    }

    public void setIsAccountActive(Integer isAccountActive) {
        this.isAccountActive = isAccountActive;
    }

    public String getTaxAccount() {
        return taxAccount;
    }

    public void setTaxAccount(String taxAccount) {
        this.taxAccount = taxAccount;
    }

    public BigDecimal getAccountRate() {
        return accountRate;
    }

    public void setAccountRate(BigDecimal accountRate) {
        this.accountRate = accountRate;
    }

    public Integer getVendorType() {
        return vendorType;
    }

    public void setVendorType(Integer vendorType) {
        this.vendorType = vendorType;
    }

    public String getContactUser() {
        return contactUser;
    }

    public void setContactUser(String contactUser) {
        this.contactUser = contactUser;
    }

    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    public Date getAuditUpdateTime() {
        return auditUpdateTime;
    }

    public void setAuditUpdateTime(Date auditUpdateTime) {
        this.auditUpdateTime = auditUpdateTime;
    }

    public String getAuditUpdateUser() {
        return auditUpdateUser;
    }

    public void setAuditUpdateUser(String auditUpdateUser) {
        this.auditUpdateUser = auditUpdateUser;
    }

    public String getAuditRemark() {
        return auditRemark;
    }

    public void setAuditRemark(String auditRemark) {
        this.auditRemark = auditRemark;
    }

    public Integer getAccountPeriodType() {
        return accountPeriodType;
    }

    public void setAccountPeriodType(Integer accountPeriodType) {
        this.accountPeriodType = accountPeriodType;
    }

    public Integer getIsReplenishment() {
        return isReplenishment;
    }

    public void setIsReplenishment(Integer isReplenishment) {
        this.isReplenishment = isReplenishment;
    }

    public BigDecimal getMinOrderAmount() {
        return minOrderAmount;
    }

    public void setMinOrderAmount(BigDecimal minOrderAmount) {
        this.minOrderAmount = minOrderAmount;
    }

    public Integer getIsStorePurchase() {
        return isStorePurchase;
    }

    public void setIsStorePurchase(Integer isStorePurchase) {
        this.isStorePurchase = isStorePurchase;
    }

    public Integer getDeliveryDay() {
        return deliveryDay;
    }

    public void setDeliveryDay(Integer deliveryDay) {
        this.deliveryDay = deliveryDay;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getIsQuality() {
        return isQuality;
    }

    public void setIsQuality(Integer isQuality) {
        this.isQuality = isQuality;
    }

    public String getDeliveryPassword() {
        return deliveryPassword;
    }

    public void setDeliveryPassword(String deliveryPassword) {
        this.deliveryPassword = deliveryPassword;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public Integer getIsSelfPurchase() {
        return isSelfPurchase;
    }

    public void setIsSelfPurchase(Integer isSelfPurchase) {
        this.isSelfPurchase = isSelfPurchase;
    }

    public String getArrivalDeadline() {
        return arrivalDeadline;
    }

    public void setArrivalDeadline(String arrivalDeadline) {
        this.arrivalDeadline = arrivalDeadline;
    }

    public Boolean getNeedVms() {
        return needVms;
    }

    public void setNeedVms(Boolean needVms) {
        this.needVms = needVms;
    }

    public String getSupplyProductType() {
        return supplyProductType;
    }

    public void setSupplyProductType(String supplyProductType) {
        this.supplyProductType = supplyProductType;
    }
}