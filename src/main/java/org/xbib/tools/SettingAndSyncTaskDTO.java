/*
 * Copyright (c) 2017, Quancheng-ec.com All right reserved. This software is the
 * confidential and proprietary information of Quancheng-ec.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Quancheng-ec.com.
 */
package org.xbib.tools;

import org.elasticsearch.common.settings.Settings;

import com.quancheng.yugong.domain.dto.SyncTaskDTO;

/**
 * @author shimingliu 2017年2月12日 下午10:56:59
 * @version SettingAndSyncTaskDTO.java, v 0.0.1 2017年2月12日 下午10:56:59 shimingliu
 */
public class SettingAndSyncTaskDTO {

    private Settings    setting;

    private SyncTaskDTO syncTaskDto;

    public SettingAndSyncTaskDTO(Settings setting, SyncTaskDTO syncTaskDto){
        super();
        this.setting = setting;
        this.syncTaskDto = syncTaskDto;
    }

    public Settings getSetting() {
        return setting;
    }

    public void setSetting(Settings setting) {
        this.setting = setting;
    }

    public SyncTaskDTO getSyncTaskDto() {
        return syncTaskDto;
    }

    public void setSyncTaskDto(SyncTaskDTO syncTaskDto) {
        this.syncTaskDto = syncTaskDto;
    }
}
