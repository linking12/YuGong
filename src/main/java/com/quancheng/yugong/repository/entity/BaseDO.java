/*
 * Copyright (c) 2017, Quancheng-ec.com All right reserved. This software is the
 * confidential and proprietary information of Quancheng-ec.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Quancheng-ec.com.
 */
package com.quancheng.yugong.repository.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

/**
 * @author shimingliu 2017年2月10日 上午10:05:02
 * @version BaseDO.java, v 0.0.1 2017年2月10日 上午10:05:02 shimingliu
 */
@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class BaseDO {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @Column(name = "creat_time")
    private Date    createTime;

    @Column(name = "update_time")
    private Date    updateTime;

    public Date getCreateTime() {
        return createTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @PrePersist
    protected void prePersist() {
        if (this.createTime == null) createTime = new Date();
        if (this.updateTime == null) updateTime = new Date();
    }

    @PreUpdate
    protected void preUpdate() {
        this.updateTime = new Date();
    }

    @PreRemove
    protected void preRemove() {
        this.updateTime = new Date();
    }

    @Override
    public String toString() {
        return "BaseDO [id=" + id + ", createTime=" + createTime + ", updateTime=" + updateTime + "]";
    }

}
