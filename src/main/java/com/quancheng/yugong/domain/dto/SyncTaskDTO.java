/*
 * Copyright (c) 2017, Quancheng-ec.com All right reserved. This software is the
 * confidential and proprietary information of Quancheng-ec.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Quancheng-ec.com.
 */
package com.quancheng.yugong.domain.dto;

import static org.elasticsearch.common.settings.Settings.settingsBuilder;

import org.elasticsearch.common.settings.Settings;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.quancheng.yugong.repository.SyncTaskDao;
import com.quancheng.yugong.repository.SyncTaskStateDao;
import com.quancheng.yugong.repository.entity.SyncTaskDO;
import com.quancheng.yugong.repository.entity.SyncTaskStateDO;

/**
 * @author shimingliu 2017年2月10日 下午3:18:34
 * @version SyncTaskDTO.java, v 0.0.1 2017年2月10日 下午3:18:34 shimingliu
 */
public class SyncTaskDTO {

    private final String           setting;

    private final SyncTaskDao      syncTaskDao;

    private final SyncTaskStateDao syncTaskStateDao;

    private final String           index;

    private final String           type;

    public SyncTaskDTO(String setting, SyncTaskDao syncTaskDao, SyncTaskStateDao syncTaskStateDao){
        JsonObject settingJson = new JsonParser().parse(setting).getAsJsonObject();
        JsonObject jdbcJson = settingJson.get("jdbc").getAsJsonObject();
        this.index = jdbcJson.get("index").getAsString();
        this.type = jdbcJson.get("type").getAsString();
        this.setting = setting;
        this.syncTaskDao = syncTaskDao;
        this.syncTaskStateDao = syncTaskStateDao;
    }

    public boolean save() {
        SyncTaskDO taskDo = new SyncTaskDO();
        taskDo.setIndex(index);
        taskDo.setType(type);
        taskDo.setSetting(setting);
        return syncTaskDao.save(taskDo).getId() != 0;
    }

    public String getSetting() {
        return this.setting;
    }

    public Settings getConfigSetting() {
        return settingsBuilder().loadFromSource(this.setting).build();
    }

    public SyncTaskStateDTO getSyncStateDTO() {
        SyncTaskDO taskDo = syncTaskDao.findTaskByIndexAndType(index, type);
        SyncTaskStateDO stateDo = taskDo.getSyncTaskState();
        SyncTaskStateDTO stateDTO = new SyncTaskStateDTO(syncTaskStateDao);
        stateDTO.setTaskId(taskDo.getId());
        if (stateDo != null) {
            stateDTO.setId(stateDo.getId());
            stateDTO.setIsCanceled(stateDo.getIsCanceled());
            stateDTO.setIsDeleted(stateDo.getIsDeleted());
            stateDTO.setStateSetting(stateDo.getStateSetting());
        }
        return stateDTO;
    }

    public String getIndex() {
        return index;
    }

    public String getType() {
        return type;
    }

}
