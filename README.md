# 概述

yugong是一个将数据库的数据同步到ElasticSearch的一个web系统

# 功能

* 提供同步能力

# 开发说明

* 类似于定时任务调度中心，需要解决的是分布式锁的问题，本系统使用的分布式锁是使用数据库的GET_LOCK来实现

* 任务执行状态保存,以便系统宕机重启后，重新从已拉取的时间点拉取任务，类似于Mq的offset

* 由于是集成了ElasticSearch-jdbc，需要对其进行改造，改造的内容有

1.  状态保存到数据，而不是本地文件
2.  支持多任务，而不是单任务
3.  支持任务退出



# Quick Start

* http：//yugong.dev.quancheng-ec.com

* 添加spring-boot-saluki依赖

