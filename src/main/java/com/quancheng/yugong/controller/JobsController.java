/*
 * Copyright (c) 2017, Quancheng-ec.com All right reserved. This software is the
 * confidential and proprietary information of Quancheng-ec.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Quancheng-ec.com.
 */
package com.quancheng.yugong.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.Gson;
import com.quancheng.yugong.service.TaskBizService;
import com.quancheng.yugong.vo.SyncTaskVO;

/**
 * @author shimingliu 2017年2月13日 下午4:01:25
 * @version JobsController.java, v 0.0.1 2017年2月13日 下午4:01:25 shimingliu
 */
@Controller
public class JobsController {

    @Autowired
    private TaskBizService syncTaskService;

    @RequestMapping(value = "/jobs", method = RequestMethod.GET)
    public String index(Model model,
                        @PageableDefault(value = 5, sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable) {
        Page<SyncTaskVO> tasks = syncTaskService.listAllTask(pageable);
        PageWrapper<SyncTaskVO> page = new PageWrapper<SyncTaskVO>(tasks, "/jobs");
        System.out.println(new Gson().toJson(page));
        model.addAttribute("jobs", page.getContent());
        model.addAttribute("page", page);
        return "/task/tasks";
    }
}
