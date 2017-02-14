/*
 * Copyright (c) 2017, Quancheng-ec.com All right reserved. This software is the
 * confidential and proprietary information of Quancheng-ec.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Quancheng-ec.com.
 */
package com.quancheng.yugong.service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.xbib.tools.JDBCImporter;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.quancheng.yugong.dto.SyncTaskDTO;
import com.quancheng.yugong.repository.DistributedLock;
import com.quancheng.yugong.repository.SyncTaskDao;
import com.quancheng.yugong.repository.SyncTaskStateDao;
import com.quancheng.yugong.repository.entity.SyncTaskDO;
import com.quancheng.yugong.repository.entity.SyncTaskStateDO;
import com.quancheng.yugong.vo.SyncTaskVO;

/**
 * @author shimingliu 2017年2月10日 下午5:15:17
 * @version DefaultSyncTaskService.java, v 0.0.1 2017年2月10日 下午5:15:17 shimingliu
 */
@Service
public class SyncTaskServiceImpl implements SyncTaskService {

    private final static Logger            logger               = LogManager.getLogger("syncTaskService.default");

    @Autowired
    private SyncTaskStateDao               stateDao;

    @Autowired
    private SyncTaskDao                    taskDao;

    @Autowired
    private DistributedLock                distributedLock;

    private Object                         lock                 = new Object();

    private Map<String, JDBCImporter>      runningImporters     = Maps.newConcurrentMap();

    private final ScheduledExecutorService scheuledScanShutDown = Executors.newScheduledThreadPool(1);

    private final ExecutorService          jdbcSyncThreadPool   = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    @Autowired
    public void init() {
        scheuledScanShutDown.scheduleAtFixedRate(new Runnable() {

            @Override
            public void run() {
                try {
                    List<String> toDeleted = Lists.newArrayList();
                    for (Map.Entry<String, JDBCImporter> entry : runningImporters.entrySet()) {
                        JDBCImporter importer = entry.getValue();
                        if (importer.isShutdown()) {
                            toDeleted.add(entry.getKey());
                            importer.shutdown();
                        }
                    }
                    for (String indexType : toDeleted) {
                        runningImporters.remove(indexType);
                    }
                } catch (Throwable e) {
                    logger.error(e.getMessage(), e);
                }

            }
        }, 0, 30, TimeUnit.MINUTES);
    }

    private String getLocalHost() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Boolean submitSyncTask(String syncSetting) {
        SyncTaskDTO syncTaskDTO = new SyncTaskDTO(syncSetting, taskDao, stateDao);
        synchronized (lock) {
            SyncTaskDO taskDo = new SyncTaskDO();
            taskDo.setIndex(syncTaskDTO.getIndex());
            taskDo.setType(syncTaskDTO.getType());
            taskDo.setSetting(syncTaskDTO.getSetting());
            taskDo.setExcuteNode(getLocalHost());
            Boolean savedSuccess = taskDao.save(taskDo).getId() != 0;
            if (savedSuccess) {
                jdbcSyncThreadPool.submit(new Runnable() {

                    @Override
                    public void run() {
                        runTask(syncTaskDTO);
                    }
                });
                return true;
            }
            return false;
        }
    }

    @Override
    public Boolean cancelSyncTask(String index, String type) {
        SyncTaskDO taskDo = taskDao.findTaskByIndexAndType(index, type);
        SyncTaskStateDO taskState = taskDo.getSyncTaskState();
        taskState.setIsCanceled(true);
        stateDao.save(taskState);
        String key = this.buildKey(index, type);
        if (runningImporters.containsKey(key)) {
            try {
                runningImporters.get(key).shutdown();
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        return true;
    }

    @Override
    public Boolean querySyncTask(String index, String type) {
        return null;
    }

    @Override
    public Page<SyncTaskVO> queryAll(Pageable pageable) {
        Page<SyncTaskDO> syncTaskDoList = taskDao.findAll(pageable);
        Page<SyncTaskVO> syncTaskVoList = syncTaskDoList.map(new Converter<SyncTaskDO, SyncTaskVO>() {

            @Override
            public SyncTaskVO convert(SyncTaskDO source) {
                SyncTaskVO syncTaskVo = new SyncTaskVO();
                syncTaskVo.setCreateTime(source.getCreateTime());
                syncTaskVo.setId(source.getId());
                syncTaskVo.setIndex(source.getIndex());
                syncTaskVo.setType(source.getType());
                syncTaskVo.setSourceSetting(source.getSetting());
                syncTaskVo.setExcuteNode(source.getExcuteNode());
                SyncTaskStateDO stateDO = source.getSyncTaskState();
                if (stateDO != null) {
                    syncTaskVo.setStateSetting(stateDO.getStateSetting());
                    syncTaskVo.setStateUpdateTime(stateDO.getUpdateTime());
                }
                return syncTaskVo;
            }

        });
        return syncTaskVoList;
    }

    private void runTask(SyncTaskDTO syncTaskDTO) {
        String key = buildKey(syncTaskDTO.getIndex(), syncTaskDTO.getType());
        try {
            Integer locked = distributedLock.getLock(key, 0);
            if (locked == 1) {
                JDBCImporter jdbcImporter = new JDBCImporter();
                jdbcImporter.run(syncTaskDTO);
                runningImporters.put(key, jdbcImporter);
            }
        } catch (Throwable e) {
            logger.error(e.getMessage(), e);
            distributedLock.release(key);
        }

    }

    private String buildKey(String index, String type) {
        return index + "_" + type;
    }

}
