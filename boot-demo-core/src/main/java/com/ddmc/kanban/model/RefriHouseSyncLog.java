package com.ddmc.kanban.model;

import java.util.Date;

/**
 * @Description Automatically generated by MBG.
 * @Author ddmc
 * @Datetime @mbg.generated 2019-04-03 10:15:16
 * @Table refri_house_sync_log
*/
public class RefriHouseSyncLog {
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
     * 最近温度采集时间yyyyMMddHHmmss: last_collect_time
     * 
     * @mbg.generated 2019-04-03 10:15:16
      */
    private Long lastCollectTime;

    /**
     * 最近同步时间yyyyMMddHHmmss: last_sync_time
     * 
     * @mbg.generated 2019-04-03 10:15:16
      */
    private Long lastSyncTime;

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

    public Long getLastCollectTime() {
        return lastCollectTime;
    }

    public void setLastCollectTime(Long lastCollectTime) {
        this.lastCollectTime = lastCollectTime;
    }

    public Long getLastSyncTime() {
        return lastSyncTime;
    }

    public void setLastSyncTime(Long lastSyncTime) {
        this.lastSyncTime = lastSyncTime;
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