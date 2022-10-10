package com.fine.dynamic.bean;


import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: FanBo
 * @Description:
 * @Date: Created in 20:30 2022/2/21
 */
public abstract class AbstractDynamicFactoryBean<T> implements FactoryBean<T> {
//    public static final ThreadLocal<String> ALL_ROUTE_TAG = new ThreadLocal<>();
//    private final ThreadLocal<String> tagOfThread = new ThreadLocal<String>();
    private ApplicationContext ac;
    private final Class parentClz;
    private Map<String, Object> mapChild = new ConcurrentHashMap<String, Object>();

    public abstract void setTagOfBean(String tag);
    public abstract String getTagOfBean();

    public AbstractDynamicFactoryBean(ApplicationContext applicationContext, Class<? extends TagAware> clz){
        ac = applicationContext;
        parentClz= clz;
        Map<String, Object> mapChildBean = applicationContext.getBeansOfType(parentClz);
        System.out.println(mapChildBean);
        for(String name:mapChildBean.keySet()){
            Tag tag = applicationContext.findAnnotationOnBean(name, Tag.class);
            if(tag!=null && !tag.value().isEmpty()){
                mapChild.put(tag.value(), mapChildBean.get(name));
            }
        }
        System.out.println(mapChild);
    }

    public int reload(ApplicationContext applicationContext, TagAware childBean){
        String childTag=null;
        ApplicationContext context=applicationContext;
        if(context==null){
            context=ac;
        }
        int n = 0;
        if(childBean!=null){
            if(!parentClz.isInstance(childBean)) {
                return n;
            }
            childTag=childBean.getClass().getDeclaredAnnotation(Tag.class).value();
        };
        if(childBean==null){
            mapChild.clear();
        }
        Map<String, Object> mapChildBean = context.getBeansOfType(parentClz);
        for(String name:mapChildBean.keySet()){
            Tag tag = context.findAnnotationOnBean(name, Tag.class);
            if(tag==null || tag.value().isEmpty()){
                continue;
            }
            if(childTag!=null && !childTag.isEmpty() && !tag.equals(childTag)){
                continue;
            }
            mapChild.put(tag.value(), mapChildBean.get(name));
            n++;
        }
        return n;
    }

    @Override
    public T getObject() throws Exception {
        InvocationHandler invocationHandler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if("setTagOfContext".equals(method.getName())&&args!=null&&args[0]!=null){
                    setTagOfBean((String)args[0]);
                    return null;
                }
                if("getTagOfContext".equals(method.getName())){
                    return getTagOfBean();
                }
                Object obj = mapChild.get(getTagOfBean());
                if (obj==null){
                    return null;
                }
                return method.invoke(obj, args);
            }
        };

        return (T) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{parentClz},
                invocationHandler);
    }

    @Override
    public Class<?> getObjectType() {
        return parentClz;
    }
}
