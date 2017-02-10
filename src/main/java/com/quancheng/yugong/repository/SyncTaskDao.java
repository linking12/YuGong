/*
 * Copyright (c) 2017, Quancheng-ec.com All right reserved. This software is the
 * confidential and proprietary information of Quancheng-ec.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Quancheng-ec.com.
 */
package com.quancheng.yugong.repository;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.quancheng.yugong.repository.entity.SyncTaskDO;

/**
 * @author shimingliu 2017年2月9日 下午7:47:11
 * @version IndexConfigRepository.java, v 0.0.1 2017年2月9日 下午7:47:11 shimingliu
 */
@Repository
public interface SyncTaskDao extends PagingAndSortingRepository<SyncTaskDO, Integer> {

    @Query("select task from SyncTaskDO task where task.index = :index and task.type = :type")
    SyncTaskDO findTaskByIndexAndType(@Param("index") String index, @Param("type") String type);
}
