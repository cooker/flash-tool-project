package org.grant.springboot.magic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


/**
 * grant
 * 21/8/2019 3:11 PM
 * 描述：
 */
@Slf4j
@Configuration
@EnableConfigurationProperties({YamlLoaderProperties.class})
@ConditionalOnProperty(prefix = "yaml.loader", name = "enable", havingValue = "true")
public class YmlLoaderConfig {

    @Autowired YamlLoaderProperties properties;
    @Autowired PropertySourcesPlaceholderConfigurer configurer;

    @PostConstruct
    public void init(){
        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        log.info("================= 自定义yaml加载 =================");
        List<ClassPathResource> resources = Arrays.asList(properties.getFiles().split(",")).stream().map((s)->{
            log.info("load yaml：" + s);
            return new ClassPathResource(s);
        }).collect(Collectors.toList());
		yaml.setResources(resources.toArray(new ClassPathResource[0]));//class引入
        configurer.setProperties(yaml.getObject());
    }
}
