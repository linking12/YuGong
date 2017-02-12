/*
 * Copyright (c) 2017, Quancheng-ec.com All right reserved. This software is the
 * confidential and proprietary information of Quancheng-ec.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Quancheng-ec.com.
 */
package com.quancheng.yugong;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.quancheng.yugong.service.SyncTaskService;

/**
 * @author shimingliu 2017年2月12日 下午8:22:35
 * @version SyncTaskServiceTest.java, v 0.0.1 2017年2月12日 下午8:22:35 shimingliu
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = YuGongLancher.class)
@WebAppConfiguration
public class SyncTaskServiceTest {

    @Autowired
    private SyncTaskService taskService;
    
    private String configSetting =" {"
                                        + "\"type\" : \"jdbc\", "
                                        +"\"jdbc\" : {"
                                        +"    \"url\" : \"jdbc:mysql://192.168.99.100:3306/yugong\","
                                        +"    \"schedule\" : \"0 0-59 0-23 ? * *\","
                                        +"    \"user\" : \"root\","
                                        +"    \"password\" : \"123456\","
                                        +"    \"sql\" : [{"
                                        +"            \"statement\": \"SELECT * FROM test where time > ?\","
                                        +"            \"parameter\" : [ \"$metrics.lastexecutionstart\" ]"
                                        +"            }"
                                        +"        ],"
                                        +"     \"index\" : \"saluki\","
                                        +"     \"type\" : \"monitor\","
                                        +"     \"metrics\": {"
                                        +"         \"enabled\" : true"
                                        +"     }"
                                        +" }"
                                  +" }";

    @Test
    public void submitTask() {
        taskService.submitSyncTask(configSetting);
    }
}
