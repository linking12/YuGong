/*
 * Copyright (c) 2017, Quancheng-ec.com All right reserved. This software is the
 * confidential and proprietary information of Quancheng-ec.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Quancheng-ec.com.
 */
package com.quancheng.yugong.service;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xbib.tools.JDBCImporter;

import com.quancheng.yugong.common.Constants;
import com.quancheng.yugong.dto.SyncTaskDTO;
import com.quancheng.yugong.repository.SyncTaskDao;
import com.quancheng.yugong.repository.SyncTaskStateDao;
import com.quancheng.yugong.repository.entity.SyncTaskDO;

/**
 * @author shimingliu 2017年2月14日 下午4:41:38
 * @version RestartServiceImpl.java, v 0.0.1 2017年2月14日 下午4:41:38 shimingliu
 */
@Component
public class TaskScheduleAndExecuteComponent {

    private static final Logger            logger               = LoggerFactory.getLogger(TaskScheduleAndExecuteComponent.class);

    @Autowired
    private SyncTaskDao                    taskDao;

    @Autowired
    private SyncTaskStateDao               stateDao;

    @Autowired
    private TaskLocalStoregeComponent      taskLocalStoreService;

    private final ScheduledExecutorService scheuledScanShutDown = Executors.newScheduledThreadPool(1);

    @PostConstruct
    public void init() {
        scheuledScanShutDown.scheduleAtFixedRate(new Runnable() {

            @Override
            public void run() {
                try {
                    scheduleDeleteTask();
                    scheduleDeleteFile();
                } catch (Throwable e) {
                    logger.error(e.getMessage(), e);
                }

            }
        }, 0, 12, TimeUnit.HOURS);
        initializatTask();
    }

    private void scheduleDeleteTask() {
        List<Pair<String, String>> shutDownTasks = taskLocalStoreService.getShutDownTasks();
        for (Pair<String, String> shutDownTask : shutDownTasks) {
            String index = shutDownTask.getLeft();
            String type = shutDownTask.getRight();
            taskLocalStoreService.delete(index, type);
        }
    }

    private void scheduleDeleteFile() {
        File file = new File(Constants.CSV_UPLOAD_DIR);
        if (file != null && file.isDirectory()) {
            for (File deleteFile : file.listFiles()) {
                deleteFile.delete();
            }
        }
    }

    private void initializatTask() {
        Iterable<SyncTaskDO> tasks = taskDao.findAll();
        Iterator<SyncTaskDO> it = tasks.iterator();
        while (it.hasNext()) {
            SyncTaskDO taskDo = it.next();
            boolean isNotRun = taskDo.getSyncTaskState() == null || !taskDo.getSyncTaskState().getIsCanceled();
            if (isNotRun) {
                saveAndRunTask(taskDo.getSetting());
            }
        }
    }

    private void saveAndRunTask(String setting) {
        SyncTaskDTO syncTaskDTO = new SyncTaskDTO(setting, taskDao, stateDao);
        String index = syncTaskDTO.getIndex();
        String type = syncTaskDTO.getType();
        final JDBCImporter importer = new JDBCImporter();
        try {
            // step1: saveTask into local
            taskLocalStoreService.save(index, type, importer);
            // step2 : run task
            executeTask(syncTaskDTO, importer);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    public void executeTask(SyncTaskDTO syncTaskDTO, JDBCImporter importer) {
        Thread syncTask = new Thread() {

            @Override
            public void run() {
                logger.info("*******begin to run task " + syncTaskDTO.getIndex() + "***********");
                importer.run(syncTaskDTO);
                logger.info("*******end to run task " + syncTaskDTO.getIndex() + "***********");
            }
        };
        syncTask.setName(syncTaskDTO.getIndex() + "-" + syncTaskDTO.getType() + "task");
        syncTask.start();
    }

}
