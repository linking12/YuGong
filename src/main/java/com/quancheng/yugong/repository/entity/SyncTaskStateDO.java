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
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author shimingliu 2017年2月9日 下午7:45:37
 * @version IndexStatEntity.java, v 0.0.1 2017年2月9日 下午7:45:37 shimingliu
 */
@Entity
@Table(name = "yugong_synctask_state")
public class SyncTaskStateDO extends BaseDO {

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sync_task_id")
    private SyncTaskDO syncTask;

    @Lob
    @Column(name = "sync_state_setting")
    private String     stateSetting;

    @Column(name = "is_canceled")
    private Boolean    isCanceled = false;

    public String getStateSetting() {
        return stateSetting;
    }

    public void setStateSetting(String stateSetting) {
        this.stateSetting = stateSetting;
    }

    public SyncTaskDO getSyncTask() {
        return syncTask;
    }

    public void setSyncTask(SyncTaskDO syncTask) {
        this.syncTask = syncTask;
    }

    public Boolean getIsCanceled() {
        return isCanceled;
    }

    public void setIsCanceled(Boolean isCanceled) {
        this.isCanceled = isCanceled;
    }

    @Override
    public String toString() {
        return "SyncTaskStateDO [stateSetting=" + stateSetting + ", syncTask=" + syncTask + "]";
    }

}
