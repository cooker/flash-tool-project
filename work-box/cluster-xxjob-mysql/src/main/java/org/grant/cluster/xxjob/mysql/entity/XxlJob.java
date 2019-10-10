package org.grant.cluster.xxjob.mysql.entity;

import lombok.Data;

/**
 * grant
 * 8/10/2019 11:36 AM
 * 描述：
 */
@Data
public class XxlJob {

    private  Long id;
    //定时任务名
    private  String taskName;
    //0-开始 1-完成
    private  Integer done;
    private  String execIp;
    //失效时间
    private  Long expireTime;
}