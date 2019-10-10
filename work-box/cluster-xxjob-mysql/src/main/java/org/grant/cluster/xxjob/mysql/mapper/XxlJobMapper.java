package org.grant.cluster.xxjob.mysql.mapper;

import org.apache.commons.lang3.math.NumberUtils;
import org.grant.cluster.xxjob.mysql.entity.XxlJob;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * grant
 * 8/10/2019 2:13 PM
 * 描述：
 */
public class XxlJobMapper implements RowMapper<XxlJob> {

    @Override
    public XxlJob mapRow(ResultSet rs, int i) throws SQLException {
        XxlJob xxlJob = new XxlJob();
        xxlJob.setId(NumberUtils.toLong(rs.getString("id"), 0));
        xxlJob.setTaskName(rs.getString("task_name"));
        xxlJob.setDone(NumberUtils.toInt(rs.getString("done"), 0));
        xxlJob.setExecIp(rs.getString("exec_ip"));
        xxlJob.setExpireTime(NumberUtils.toLong(rs.getString("expire_time"), 0));
        return xxlJob;
    }
}
