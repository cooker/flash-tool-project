package org.grant.springboot.controller;

import org.grant.springboot.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * grant
 * 28/8/2019 2:22 PM
 * 描述：
 */
@Service
public class Hs {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @DataSource("qq")
    public void sa(){
        System.out.println("sssssssss");
//        System.out.println(jdbcTemplate.getDataSource());
        System.out.println(jdbcTemplate.queryForMap("SELECT 1 from dual"));
    }

    @DataSource()
    public void xx(){
        System.out.println("22222222");
        System.out.println(jdbcTemplate.queryForMap("SELECT * from t_tax_code limit 1"));
//        System.out.println(jdbcTemplate.queryForMap("select 1 from dual"));
    }
}
