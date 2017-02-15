/*
 * Copyright (c) 2017, Quancheng-ec.com All right reserved. This software is the
 * confidential and proprietary information of Quancheng-ec.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Quancheng-ec.com.
 */
package com.quancheng.yugong.service;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xbib.tools.JDBCImporter;

import com.quancheng.yugong.common.NamedThreadFactory;
import com.quancheng.yugong.dto.SyncTaskDTO;
import com.quancheng.yugong.repository.SyncTaskDao;
import com.quancheng.yugong.repository.SyncTaskStateDao;
import com.quancheng.yugong.repository.entity.SyncTaskDO;

/**
 * @author shimingliu 2017年2月14日 下午4:41:38
 * @version RestartServiceImpl.java, v 0.0.1 2017年2月14日 下午4:41:38 shimingliu
 */
@Component
public class TaskExecuteComponent {

    private final static Logger            logger               = LogManager.getLogger("taskExecute.default");

    @Autowired
    private SyncTaskDao                    taskDao;

    @Autowired
    private SyncTaskStateDao               stateDao;

    @Autowired
    private TaskLocalStoregeComponent        taskLocalStoreService;

    private final ScheduledExecutorService scheuledScanShutDown = Executors.newScheduledThreadPool(1);

    private final ExecutorService          syncTaskExecutor     = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(),
                                                                                               new NamedThreadFactory("CheckServiceTimer",
                                                                                                                      true));

    @PostConstruct
    public void init() {
        scheuledScanShutDown.scheduleAtFixedRate(new Runnable() {

            @Override
            public void run() {
                try {
                    scheduleDeleteTask();
                } catch (Throwable e) {
                    logger.error(e.getMessage(), e);
                }

            }
        }, 0, 30, TimeUnit.MINUTES);
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

    private void initializatTask() {
        Iterable<SyncTaskDO> tasks = taskDao.findAll();
        Iterator<SyncTaskDO> it = tasks.iterator();
        while (it.hasNext()) {
            SyncTaskDO taskDo = it.next();
            if (!taskDo.getSyncTaskState().getIsCanceled()) {
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
        syncTaskExecutor.execute(new Runnable() {

            @Override
            public void run() {
                importer.run(syncTaskDTO);
            }
        });
    }

}
