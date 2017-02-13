/*
 * Copyright (c) 2017, Quancheng-ec.com All right reserved. This software is the
 * confidential and proprietary information of Quancheng-ec.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Quancheng-ec.com.
 */
package com.quancheng.yugong.repository.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @author shimingliu 2017年2月9日 下午7:45:16
 * @version IndexConfigEntity.java, v 0.0.1 2017年2月9日 下午7:45:16 shimingliu
 */
@Entity
@Table(name = "yugong_synctask", uniqueConstraints = @UniqueConstraint(name = "uk_index_type", columnNames = { "es_index",
                                                                                                               "es_type" }))
public class SyncTaskDO extends BaseDO {

    @Column(name = "es_index")
    private String          index;

    @Column(name = "es_type")
    private String          type;

    @Column(name = "excute_machine")
    private String          excuteNode;

    @Lob
    @Column(name = "sync_setting")
    private String          setting;

    @OneToOne(mappedBy = "syncTask", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private SyncTaskStateDO syncTaskState;

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSetting() {
        return setting;
    }

    public void setSetting(String setting) {
        this.setting = setting;
    }

    public SyncTaskStateDO getSyncTaskState() {
        return syncTaskState;
    }

    public void setSyncTaskState(SyncTaskStateDO syncTaskState) {
        this.syncTaskState = syncTaskState;
    }

    public String getExcuteNode() {
        return excuteNode;
    }

    public void setExcuteNode(String excuteNode) {
        this.excuteNode = excuteNode;
    }

    @Override
    public String toString() {
        return "SyncTaskDO [index=" + index + ", type=" + type + ", excuteNode=" + excuteNode + ", setting=" + setting
               + ", syncTaskState=" + syncTaskState + "]";
    }

}
