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

import com.quancheng.yugong.service.TaskBizService;

/**
 * @author shimingliu 2017年2月12日 下午8:22:35
 * @version SyncTaskServiceTest.java, v 0.0.1 2017年2月12日 下午8:22:35 shimingliu
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = YuGongLancher.class)
@WebAppConfiguration
public class SyncTaskServiceTest {

    @Autowired
    private TaskBizService taskService;
    
    
    private String sql = "SELECT *"
            +"FROM"
            +"(SELECT a.id,"
            +"        a.company_id,"
            +"        a.cn_name,"
            +"        a.cn_first_name,"
            +"        a.cn_last_name,"
            +"        a.en_name,"
            +"        a.en_first_name,"
            +"        a.en_last_name,"
            +"        a.gender,"
            +"        a.staff_id,"
            +"        a.company_email,"
            +"        a.company_phone,"
            +"        a.company_mobile,"
            +"        a.title,"
            +"        a.level,"
            +"        a.hire_date,"
            +"        a.id_card_no,"
            +"        a.passport_no,"
            +"        a.report_account_id,"
            +"        a.status,"
            +"        a.custom1,"
            +"        a.custom2,"
            +"        a.custom3,"
            +"        a.custom4,"
            +"        a.contacts,"
            +"        u.id AS user_id,"
            +"        u.login_id,"
            +"        u.nick_name,"
            +"        u.mobile,"
            +"        u.email,"
            +"        u.platform_type,"
            +"        u.pwd,"
            +"        u.pwd_expire,"
            +"        u.pwd_salt,"
            +"        u.pwd_encryption,"
            +"        u.reg_time AS user_reg_time,"
            +"        u.avatar_url,"
            +"        u.status AS user_status,"
            +"        u.weixin_openid,"
            +"        u.dingding_openid,"
            +"        u.weibo_openid,"
            +"        u.google_openid,"
            +"        u.facebook_openid,"
            +"        u.twitter_openid,"
            +"        u.oauth_openid1,"
            +"        u.oauth_openid2,"
            +"        u.oauth_openid3,"
            +"        u.oauth_openid4,"
            +"        u.custom1 AS user_custom1,"
            +"        u.custom2 AS user_custom2,"
            +"        u.custom3 AS user_custom3,"
            +"        u.custom4 AS user_custom4,"
            +"        u.gmt_activated AS activated_time,"
            +"        c.pid AS company_pid,"
            +"        c.type AS company_type,"
            +"        c.cn_name AS company_cn_name,"
            +"        c.en_name AS company_en_name,"
            +"        c.nick_name AS company_nick_name,"
            +"        c.code AS company_code,"
            +"        c.legal_person,"
            +"        c.logo AS company_logo,"
            +"        c.workfroce,"
            +"        c.register_capital,"
            +"        c.register_capital_unit,"
            +"        c.reg_date AS company_reg_date,"
            +"        c.contact_name AS company_contact_name,"
            +"        c.contact_phone AS company_contact_phone,"
            +"        c.address AS company_address,"
            +"        c.status AS company_status,"
            +"        c.custom1 AS company_custom1,"
            +"        c.custom2 AS company_custom2,"
            +"        c.custom3 AS company_custom3,"
            +"        c.custom4 AS company_custom4,"
            +"        c.business_scope,"
            +"        c.industry,"
            +"        c.`desc` AS company_desc,"
            +"        ga.group_id,"
            +"        ga.type AS ga_type,"
            +"        ga.status AS ga_status,"
            +"        g.name AS group_name,"
            +"        g.type AS group_type,"
            +"        g.code AS group_code,"
            +"        g.`desc` AS group_desc,"
            +"        g.pid AS group_pid,"
            +"        g.lft AS group_lft,"
            +"        g.rgt AS group_rgt,"
            +"        g.level AS group_level,"
            +"        g.status AS group_status,"
            +"        g.custom1 AS group_custom1,"
            +"        g.custom2 AS group_custom2,"
            +"        g.custom3 AS group_custom3,"
            +"        g.custom4 AS group_custom4,"
            +"        GREATEST(a.gmt_modified, c.gmt_modified, u.gmt_modified, g.gmt_modified, ga.gmt_modified) AS update_time"
            +" FROM zeus_account AS a"
            +" LEFT JOIN zeus_company AS c ON a.company_id = c.id"
            +" LEFT JOIN (zeus_user_account_relation AS ua"
            +"            LEFT JOIN zeus_user AS u ON ua.user_id= u.id) ON a.id = ua.account_id"
            +" LEFT JOIN zeus_group_relation AS ga ON a.id = ga.account_id"
            +" LEFT JOIN zeus_group g ON ga.group_id = g.id)AS zeusimport"
         +" WHERE zeusimport.update_time >?";
    
    
    private String configSetting =" {"
                                        + "\"type\" : \"jdbc\", "
                                        +"\"jdbc\" : {"
                                        +"    \"url\" : \"jdbc:mysql://apollo-test-yun.mysql.rds.aliyuncs.com:3306/apollo\","
                                        +"    \"schedule\" : \"0 0-59 0-23 ? * *\","
                                        +"    \"user\" : \"qc_test\","
                                        +"    \"password\" : \"Qcwl123456\","
                                        +"    \"sql\" : [{"
                                        +"            \"statement\": "
                                        +sql
                                        +    ","
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
        taskService.submitTask(configSetting);
    }
}
