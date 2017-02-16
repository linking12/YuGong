/*
 * Copyright (c) 2017, Quancheng-ec.com All right reserved. This software is the
 * confidential and proprietary information of Quancheng-ec.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Quancheng-ec.com.
 */
package com.quancheng.yugong.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author shimingliu 2017年2月15日 下午6:19:30
 * @version UploadController.java, v 0.0.1 2017年2月15日 下午6:19:30 shimingliu
 */
@Controller
public class UploadController {

    private static final String CSV_UPLOAD_DIR = System.getProperty("user.dir") + "/csv/";

    @RequestMapping(value = "/uploadList", method = RequestMethod.GET)
    public String index(Model model) {
        File file = new File(CSV_UPLOAD_DIR);
        model.addAttribute("files", file.listFiles());
        return "upload/uploadFile";
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String uploadingPost(@RequestParam("uploadingFiles") MultipartFile[] uploadingFiles) throws IOException {
        for (MultipartFile uploadedFile : uploadingFiles) {
            File file = new File(CSV_UPLOAD_DIR + uploadedFile.getOriginalFilename());
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            uploadedFile.transferTo(file);
        }

        return "redirect:/uploadList";
    }

}
