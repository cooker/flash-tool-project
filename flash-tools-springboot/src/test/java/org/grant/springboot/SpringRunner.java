package org.grant.springboot;

import org.grant.springboot.controller.Hs;
import org.grant.springboot.magic.datasource.DynamicDataSourceConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.context.ConfigurationPropertiesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

/**
 * grant
 * 21/8/2019 5:22 PM
 * 描述：
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpringRunner.class)
@SpringBootApplication
public class SpringRunner {

//    private RestTemplate restTemplate = new RestTemplate();
//
//    @Test
//    public void test1(){
//        String str = restTemplate.getForObject("http://127.0.0.1:8080/hello", String.class);
//        System.out.println(str);
//    }

    @Autowired
    Hs hs;

    @Test
    public void ss(){
        hs.sa();
        hs.xx();
    }
}
