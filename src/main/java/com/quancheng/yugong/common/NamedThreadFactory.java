/*
 * Copyright (c) 2017, Quancheng-ec.com All right reserved. This software is the
 * confidential and proprietary information of Quancheng-ec.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Quancheng-ec.com.
 */
package com.quancheng.yugong.common;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author shimingliu 2017年2月14日 下午6:03:31
 * @version NamedThreadFactory.java, v 0.0.1 2017年2月14日 下午6:03:31 shimingliu
 */
public class NamedThreadFactory implements ThreadFactory {

    private static final AtomicInteger POOL_SEQ   = new AtomicInteger(1);

    private final AtomicInteger        mThreadNum = new AtomicInteger(1);

    private final String               mPrefix;

    private final boolean              mDaemo;

    private final ThreadGroup          mGroup;

    public NamedThreadFactory(){
        this("pool-" + POOL_SEQ.getAndIncrement(), false);
    }

    public NamedThreadFactory(String prefix){
        this(prefix, false);
    }

    public NamedThreadFactory(String prefix, boolean daemo){
        mPrefix = prefix + "-thread-";
        mDaemo = daemo;
        SecurityManager s = System.getSecurityManager();
        mGroup = (s == null) ? Thread.currentThread().getThreadGroup() : s.getThreadGroup();
    }

    public Thread newThread(Runnable runnable) {
        String name = mPrefix + mThreadNum.getAndIncrement();
        Thread ret = new Thread(mGroup, runnable, name, 0);
        ret.setDaemon(mDaemo);
        return ret;
    }

    public ThreadGroup getThreadGroup() {
        return mGroup;
    }
}
