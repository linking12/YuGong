/*
 * Copyright (c) 2017, Quancheng-ec.com All right reserved. This software is the
 * confidential and proprietary information of Quancheng-ec.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Quancheng-ec.com.
 */
package com.quancheng.yugong.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xbib.tools.JDBCImporter;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.quancheng.yugong.repository.DistributedLock;

/**
 * @author shimingliu 2017年2月14日 下午4:46:37
 * @version TaskLoclStoregeService.java, v 0.0.1 2017年2月14日 下午4:46:37 shimingliu
 */
@Service
public class TaskLocalStoregeService {

    private final static Logger                    logger                  = LogManager.getLogger("syncTaskService.default");

    private final static String                    SPECIAL_SPLIT_CHARACTER = "-";

    @Autowired
    private DistributedLock                        distributedLock;

    private static final Map<String, JDBCImporter> JDBCImporterCache       = Maps.newConcurrentMap();

    public JDBCImporter save(String index, String type, JDBCImporter importer) throws IOException {
        String taskKey = this.buildTaskKey(index, type);
        return save(taskKey, importer);
    }

    public JDBCImporter save(String taskKey, JDBCImporter importer) throws IOException {
        Integer locked = distributedLock.getLock(taskKey, 0);
        if (locked == 1) {
            if (JDBCImporterCache.containsKey(taskKey)) {
                JDBCImporterCache.remove(taskKey);
            }
            JDBCImporterCache.putIfAbsent(taskKey, importer);
            return importer;
        } else {
            throw new IOException(String.format("this index-type %s is running in other machine", taskKey));
        }
    }

    public JDBCImporter get(String index, String type) {
        String taskKey = this.buildTaskKey(index, type);
        return get(taskKey);
    }

    public JDBCImporter get(String taskKey) {
        if (JDBCImporterCache.containsKey(taskKey)) {
            return JDBCImporterCache.get(taskKey);
        } else {
            return null;
        }
    }

    public List<Pair<String, String>> getShutDownTasks() {
        List<Pair<String, String>> shutDownTaskKeys = Lists.newArrayList();
        for (Map.Entry<String, JDBCImporter> entry : JDBCImporterCache.entrySet()) {
            JDBCImporter importer = entry.getValue();
            if (importer.isShutdown()) {
                String[] indexType = StringUtils.split(entry.getKey(), SPECIAL_SPLIT_CHARACTER);
                shutDownTaskKeys.add(new ImmutablePair<String, String>(indexType[0], indexType[1]));
            }
        }
        return shutDownTaskKeys;
    }

    public void delete(String index, String type) {
        String taskKey = this.buildTaskKey(index, type);
        if (JDBCImporterCache.containsKey(taskKey)) {
            try {
                JDBCImporter importer = JDBCImporterCache.get(taskKey);
                try {
                    importer.shutdown();
                } finally {
                    distributedLock.release(taskKey);
                    JDBCImporterCache.remove(taskKey);
                    importer = null;// set to null for gc
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    public String buildTaskKey(String index, String type) {
        return index + SPECIAL_SPLIT_CHARACTER + type;
    }

}
