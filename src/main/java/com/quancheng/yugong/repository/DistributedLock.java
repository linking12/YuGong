/*
 * Copyright (c) 2017, Quancheng-ec.com All right reserved. This software is the
 * confidential and proprietary information of Quancheng-ec.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Quancheng-ec.com.
 */
package com.quancheng.yugong.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.quancheng.yugong.repository.entity.SyncTaskDO;

/**
 * @author shimingliu 2017年2月10日 下午5:33:31
 * @version SyncTaskLockDao.java, v 0.0.1 2017年2月10日 下午5:33:31 shimingliu
 */
@Repository
public interface DistributedLock extends PagingAndSortingRepository<SyncTaskDO, Integer> {

    @Query(value = "SELECT GET_LOCK(?1, ?2)", nativeQuery = true)
    Integer getLock(String key, Integer timeOut);

    @Query(value = "SELECT GET_LOCK(?1)", nativeQuery = true)
    Integer release(String key);
}
