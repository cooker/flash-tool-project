package org.grant.cluster.xxjob.mysql.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.grant.cluster.xxjob.mysql.*;
import org.grant.cluster.xxjob.mysql.entity.XxlJob;
import org.grant.cluster.xxjob.mysql.mapper.XxlJobMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * grant
 * 8/10/2019 2:00 PM
 * 描述：
 */
@Slf4j
public abstract class XXJobService {

    @Autowired JdbcTemplate jdbcTemplate;

    public boolean verify(){
        try {
            XxlJob xxlJob = jdbcTemplate.queryForObject(CommonTool.SELECT_SQL, new XxlJobMapper(), new Object[]{getTaskName()});
            if (xxlJob == null){
                log.info("找不到定时任务配置：{}", getTaskName());
                return false;
            }
            long expireTime = xxlJob.getExpireTime();
            if (CommonTool.READY == xxlJob.getDone() || CommonTool.FINISH == xxlJob.getDone()) {
                log.debug("定时任务抢占 {}", getTaskName());
                int num = jdbcTemplate.update(CommonTool.UPDATE_SQL_START, CommonTool.START, getTaskName());
                if (num == 0) {
                    log.debug("定时任务抢占 {} 失败", getTaskName());
                    return false;
                }
            }
            if (NumberUtils.INTEGER_ZERO.equals(xxlJob.getDone())){
                if (0 == expireTime || (System.currentTimeMillis() > expireTime)) {
                    return updateStart(expireTime);
                }
                return false;
            }else {
                expireTime = System.currentTimeMillis() + CommonTool.expire;
                return updateStart(expireTime);
            }
        }catch (Exception e){
            log.debug("ERROR", e);
            return false;
        }
    }

    private boolean updateStart(long expireTime){
        expireTime = System.currentTimeMillis() + CommonTool.expire;
        int num = jdbcTemplate.update(CommonTool.UPDATE_SQL, CommonTool.START, CommonTool.getIpAddress(), expireTime, getTaskName());
        if (num > 0) {
            return true;
        }else {
            return false;
        }
    }

    public void finish(){
        try {
            int num = jdbcTemplate.update(CommonTool.UPDATE_SQL, CommonTool.FINISH, CommonTool.getIpAddress(), 0, getTaskName());
            if (num > 0) {
                log.info( getTaskName() + "定时任务完成！");
            }
        }catch (Exception e){
            log.debug("ERROR", e);
        }
    }


    protected abstract String getTaskName();
}
