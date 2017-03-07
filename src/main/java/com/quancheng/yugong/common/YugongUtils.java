/*
 * Copyright (c) 2017, Quancheng-ec.com All right reserved. This software is the
 * confidential and proprietary information of Quancheng-ec.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Quancheng-ec.com.
 */
package com.quancheng.yugong.common;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author shimingliu 2017年3月7日 下午5:12:54
 * @version YugongUtils.java, v 0.0.1 2017年3月7日 下午5:12:54 shimingliu
 */
public class YugongUtils {

    private YugongUtils(){

    }

    public static String getLocalHost() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }
}
