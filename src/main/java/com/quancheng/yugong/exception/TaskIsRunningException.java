/*
 * Copyright (c) 2017, Quancheng-ec.com All right reserved. This software is the
 * confidential and proprietary information of Quancheng-ec.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Quancheng-ec.com.
 */
package com.quancheng.yugong.exception;

import org.springframework.http.HttpStatus;

/**
 * @author shimingliu 2017年2月10日 下午7:02:06
 * @version TaskIsRunningException.java, v 0.0.1 2017年2月10日 下午7:02:06 shimingliu
 */
public class TaskIsRunningException extends AbstractYuGongHttpException {

    public TaskIsRunningException(String str){
        super(str);
        setHttpStatus(HttpStatus.LOCKED);
    }

    public TaskIsRunningException(String str, Exception e){
        super(str, e);
        setHttpStatus(HttpStatus.LOCKED);
    }
}
