package com.ddmc.kanban.model;

import java.util.Date;

/**
 * @Description Automatically generated by MBG.
 * @Author ddmc
 * @Datetime @mbg.generated 2019-04-03 10:15:16
 * @Table refrigeration_house_device
*/
public class RefrigerationHouseDevice {
    /**
     * id
     * 
     * @mbg.generated 2019-04-03 10:15:16
      */
    private Integer id;

    /**
     * 座头鲸网关ID: out_gate_id
     * 
     * @mbg.generated 2019-04-03 10:15:16
      */
    private String outGateId;

    /**
     * 终端ID: terminal_id
     * 
     * @mbg.generated 2019-04-03 10:15:16
      */
    private String terminalId;

    /**
     * 终端名称，本表只保留LD\LC: terminal_name
     * 
     * @mbg.generated 2019-04-03 10:15:16
      */
    private String terminalName;

    /**
     * 1、生效；2、失效: status
     * 
     * @mbg.generated 2019-04-03 10:15:16
      */
    private Boolean status;

    /**
     * 创建时间: create_time
     * 
     * @mbg.generated 2019-04-03 10:15:16
      */
    private Date createTime;

    /**
     * update_time
     * 
     * @mbg.generated 2019-04-03 10:15:16
      */
    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOutGateId() {
        return outGateId;
    }

    public void setOutGateId(String outGateId) {
        this.outGateId = outGateId;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public String getTerminalName() {
        return terminalName;
    }

    public void setTerminalName(String terminalName) {
        this.terminalName = terminalName;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
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