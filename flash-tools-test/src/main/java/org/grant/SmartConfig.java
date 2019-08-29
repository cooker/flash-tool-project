package org.grant;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.Aware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * grant
 * 15/8/2019 5:32 PM
 * 描述：
 */
@Component
@Order(1)
public class SmartConfig implements ApplicationContextAware {




    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        System.out.println(context.getParentBeanFactory().getClass().getName());

//                .getParentBeanFactory().addBeanPostProcessor(new BeanPostProcessor() {
//            @Override
//            public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
//                System.out.println(beanName);
//                return bean;
//            }
//
//            @Override
//            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
//                return bean;
//            }
//        });
    }
}
