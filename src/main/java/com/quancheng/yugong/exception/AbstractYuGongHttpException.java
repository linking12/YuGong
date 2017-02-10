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
 * @author shimingliu 2017年2月10日 下午7:00:09
 * @version AbstractYuGongHttpException.java, v 0.0.1 2017年2月10日 下午7:00:09 shimingliu
 */
public class AbstractYuGongHttpException extends RuntimeException {

    private static final long serialVersionUID = -1713129594004951820L;

    protected HttpStatus      httpStatus;

    public AbstractYuGongHttpException(String msg){
        super(msg);
    }

    public AbstractYuGongHttpException(String msg, Exception e){
        super(msg, e);
    }

    protected void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
