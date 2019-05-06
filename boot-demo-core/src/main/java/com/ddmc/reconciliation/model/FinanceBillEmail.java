package com.ddmc.reconciliation.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description Automatically generated by MBG.
 * @Author ddmc
 * @Datetime @mbg.generated 2019-04-28 17:38:38
 * @Table finance_bill_email
*/
public class FinanceBillEmail {
    /**
     * 主键: id
     * 
     * @mbg.generated 2019-04-28 17:38:38
      */
    private Integer id;

    /**
     * 系统应付账款: ddmc_amount
     * 
     * @mbg.generated 2019-04-28 17:38:38
      */
    private BigDecimal ddmcAmount;

    /**
     * 金蝶应付账款: jindie_amount
     * 
     * @mbg.generated 2019-04-28 17:38:38
      */
    private BigDecimal jindieAmount;

    /**
     * 差额: balance
     * 
     * @mbg.generated 2019-04-28 17:38:38
      */
    private BigDecimal balance;

    /**
     * 供应商明细: vendor_detail
     * 
     * @mbg.generated 2019-04-28 17:38:38
      */
    private String vendorDetail;

    /**
     * 单据明细: bill_detail
     * 
     * @mbg.generated 2019-04-28 17:38:38
      */
    private String billDetail;

    /**
     * 收件人邮箱: to_email
     * 
     * @mbg.generated 2019-04-28 17:38:38
      */
    private String toEmail;

    /**
     * 邮件主题: email_subject
     * 
     * @mbg.generated 2019-04-28 17:38:38
      */
    private String emailSubject;

    /**
     * 1:总账有差异 2:供应商明细有差异 3 单据明细有差异: email_type
     * 
     * @mbg.generated 2019-04-28 17:38:38
      */
    private Boolean emailType;

    /**
     * 创建时间: create_time
     * 
     * @mbg.generated 2019-04-28 17:38:38
      */
    private Date createTime;

    /**
     * 更新时间: update_time
     * 
     * @mbg.generated 2019-04-28 17:38:38
      */
    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getDdmcAmount() {
        return ddmcAmount;
    }

    public void setDdmcAmount(BigDecimal ddmcAmount) {
        this.ddmcAmount = ddmcAmount;
    }

    public BigDecimal getJindieAmount() {
        return jindieAmount;
    }

    public void setJindieAmount(BigDecimal jindieAmount) {
        this.jindieAmount = jindieAmount;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getVendorDetail() {
        return vendorDetail;
    }

    public void setVendorDetail(String vendorDetail) {
        this.vendorDetail = vendorDetail;
    }

    public String getBillDetail() {
        return billDetail;
    }

    public void setBillDetail(String billDetail) {
        this.billDetail = billDetail;
    }

    public String getToEmail() {
        return toEmail;
    }

    public void setToEmail(String toEmail) {
        this.toEmail = toEmail;
    }

    public String getEmailSubject() {
        return emailSubject;
    }

    public void setEmailSubject(String emailSubject) {
        this.emailSubject = emailSubject;
    }

    public Boolean getEmailType() {
        return emailType;
    }

    public void setEmailType(Boolean emailType) {
        this.emailType = emailType;
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