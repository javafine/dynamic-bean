package com.fine.dynamic.bean;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;

/**
 * @Author: javafine
 * @Description:
 * @Date: Created in 19:25 2022/3/4
 */
public class DynamicRegistor {

    public static Object register(ApplicationContext applicationContext, String className, String beanName){
        DefaultListableBeanFactory fty = (DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();
        System.out.println(Arrays.asList(fty.getBeanDefinitionNames()));
        if(fty.containsBeanDefinition(beanName)){
            fty.removeBeanDefinition(beanName);
        }
        BeanDefinition bean = new GenericBeanDefinition();
        bean.setBeanClassName(className);
        fty.registerBeanDefinition(beanName, bean);
        if(applicationContext.containsBean(beanName)){
            return applicationContext.getBean(beanName);
        }
        return null;
    }
}
