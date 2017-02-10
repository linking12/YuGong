/*
 * Copyright (c) 2017, Quancheng-ec.com All right reserved. This software is the
 * confidential and proprietary information of Quancheng-ec.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Quancheng-ec.com.
 */
package com.quancheng.yugong.config;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

import com.alibaba.druid.support.http.StatViewServlet;

/**
 * @author shimingliu 2017年2月10日 上午12:25:56
 * @version DruidStatViewServlet.java, v 0.0.1 2017年2月10日 上午12:25:56 shimingliu
 */

@WebServlet(urlPatterns = "/druid/*", initParams = { @WebInitParam(name = "loginUsername", value = "admin"), // 用户名
                                                     @WebInitParam(name = "loginPassword", value = "123456"), // 密码
                                                     @WebInitParam(name = "resetEnable", value = "false")// 禁用HTML页面上的“Reset
                                                                                                         // All”功能
})
public class DruidStatViewServlet extends StatViewServlet {

    private static final long serialVersionUID = -2688872071445249539L;

}
