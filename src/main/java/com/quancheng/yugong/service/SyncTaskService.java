/*
 * Copyright (c) 2017, Quancheng-ec.com All right reserved. This software is the
 * confidential and proprietary information of Quancheng-ec.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Quancheng-ec.com.
 */
package com.quancheng.yugong.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.quancheng.yugong.vo.SyncTaskVO;

/**
 * @author shimingliu 2017年2月10日 上午11:37:20
 * @version SyncTaskService.java, v 0.0.1 2017年2月10日 上午11:37:20 shimingliu
 */
public interface SyncTaskService {

    public Boolean submitSyncTask(String syncSetting);

    public Boolean cancelSyncTask(String index, String type);

    public Page<SyncTaskVO> queryAll(Pageable pageable);

}
