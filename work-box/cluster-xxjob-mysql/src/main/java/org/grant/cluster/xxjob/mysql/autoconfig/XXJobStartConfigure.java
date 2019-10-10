package org.grant.cluster.xxjob.mysql.autoconfig;

import lombok.extern.slf4j.Slf4j;
import org.grant.cluster.xxjob.mysql.mapper.CountMapper;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import static org.grant.cluster.xxjob.mysql.CommonTool.INSERT_SQL;
import static org.grant.cluster.xxjob.mysql.CommonTool.SELECT_SQL_COUNT;
import static org.grant.cluster.xxjob.mysql.CommonTool.READY;

/**
 * grant
 * 8/10/2019 11:46 AM
 * 描述：
 */
@Component
@Configuration
@Slf4j
public class XXJobStartConfigure implements ApplicationContextAware, BeanPostProcessor {
    JdbcTemplate jdbcTemplate;

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        jdbcTemplate = context.getBean(JdbcTemplate.class);
    }


    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        XXJobTask xxJobTask = AnnotationUtils.findAnnotation(bean.getClass(), XXJobTask.class);
        if (xxJobTask != null) {
            String val = xxJobTask.value();
            log.info("定时任务：{} >>> 加载", val);
            Integer num = jdbcTemplate.queryForObject(SELECT_SQL_COUNT, new CountMapper(), val);
            if (num == 0){
                jdbcTemplate.update(INSERT_SQL, val, READY);
            }
        }
        return bean;
    }
}
