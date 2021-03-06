/*
 * Copyright (c) 2017, Quancheng-ec.com All right reserved. This software is the
 * confidential and proprietary information of Quancheng-ec.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Quancheng-ec.com.
 */
package com.quancheng.yugong.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.quancheng.yugong.repository.entity.SyncTaskStateDO;

/**
 * @author shimingliu 2017年2月9日 下午7:49:58
 * @version IndexStateRepository.java, v 0.0.1 2017年2月9日 下午7:49:58 shimingliu
 */
@Repository
public interface SyncTaskStateDao extends PagingAndSortingRepository<SyncTaskStateDO, Integer> {

}
