/*
 * Copyright (c) 2017, Quancheng-ec.com All right reserved. This software is the
 * confidential and proprietary information of Quancheng-ec.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Quancheng-ec.com.
 */
package org.xbib.tools;

import org.xbib.pipeline.PipelineRequest;

/**
 * @author shimingliu 2017年2月12日 下午10:42:24
 * @version SettingAndSyncTaskDTOPipelineRequest.java, v 0.0.1 2017年2月12日 下午10:42:24 shimingliu
 */
public class SettingAndSyncTaskDTOPipelineRequest implements PipelineRequest<SettingAndSyncTaskDTO> {

    private SettingAndSyncTaskDTO settingSyncTaskDto;

    @Override
    public SettingAndSyncTaskDTO get() {
        return this.settingSyncTaskDto;
    }

    @Override
    public SettingAndSyncTaskDTOPipelineRequest set(SettingAndSyncTaskDTO settingSyncTaskDto) {
        this.settingSyncTaskDto = settingSyncTaskDto;
        return this;
    }

}
