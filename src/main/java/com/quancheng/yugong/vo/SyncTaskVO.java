/*
 * Copyright (c) 2017, Quancheng-ec.com All right reserved. This software is the
 * confidential and proprietary information of Quancheng-ec.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Quancheng-ec.com.
 */
package com.quancheng.yugong.vo;

import java.util.Date;

/**
 * @author shimingliu 2017年2月13日 下午10:08:48
 * @version SyncTaskVO.java, v 0.0.1 2017年2月13日 下午10:08:48 shimingliu
 */
public class SyncTaskVO {

    private Integer id;

    private String  index;

    private String  type;

    private String  sourceSetting;

    private String  excuteNode;

    private Date    createTime;

    private String  stateSetting;

    private Date    stateUpdateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSourceSetting() {
        return sourceSetting;
    }

    public void setSourceSetting(String sourceSetting) {
        this.sourceSetting = sourceSetting;
    }

    public String getExcuteNode() {
        return excuteNode;
    }

    public void setExcuteNode(String excuteNode) {
        this.excuteNode = excuteNode;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getStateSetting() {
        return stateSetting;
    }

    public void setStateSetting(String stateSetting) {
        this.stateSetting = stateSetting;
    }

    public Date getStateUpdateTime() {
        return stateUpdateTime;
    }

    public void setStateUpdateTime(Date stateUpdateTime) {
        this.stateUpdateTime = stateUpdateTime;
    }

}
