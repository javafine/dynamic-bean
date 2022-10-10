package com.fine.demo.dynamicbean.config;

import com.fine.demo.dynamicbean.bo.color.Color;
import com.fine.demo.dynamicbean.bo.shape.Shape;
import com.fine.dynamic.bean.DynamicFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @Author: FanBo
 * @Description:
 * @Date: Created in 11:13 2022/10/10
 */
@Configuration
public class SampleConfig
{
    @Bean
    @Primary
    public Color dynamicColor(ApplicationContext applicationContext) throws Exception {
        return new DynamicFactoryBean<Color>(applicationContext, Color.class).getObject();
    }
    @Bean
    @Primary
    public Shape dynamicShape(ApplicationContext applicationContext) throws Exception {
        return new DynamicFactoryBean<Shape>(applicationContext, Shape.class).getObject();
    }
}
