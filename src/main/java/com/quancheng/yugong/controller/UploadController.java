/*
 * Copyright (c) 2017, Quancheng-ec.com All right reserved. This software is the
 * confidential and proprietary information of Quancheng-ec.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Quancheng-ec.com.
 */
package com.quancheng.yugong.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author shimingliu 2017年2月15日 下午6:19:30
 * @version UploadController.java, v 0.0.1 2017年2月15日 下午6:19:30 shimingliu
 */
@Controller
public class UploadController {

    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public String index() {
        return "upload/uploadFile";
    }

}
