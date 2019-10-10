package org.grant.cluster.xxjob.mysql.mapper;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * grant
 * 10/10/2019 11:03 AM
 * 描述：
 */
public class CountMapper implements RowMapper<Integer> {

    @Override
    public Integer mapRow(ResultSet rs, int i) throws SQLException {
        int n = rs.getInt(1);
        return n;
    }
}
