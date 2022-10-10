package com.fine.dynamic.bean;


import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;

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
public class DynamicFactoryBean<T> implements FactoryBean<T> {
    public static final ThreadLocal<String> ALL_ROUTE_TAG = new ThreadLocal<String>();
    private final ThreadLocal<String> tagOfThread = new ThreadLocal<String>();
    private ApplicationContext ac;
    private final Class parentClz;
    private Map<String, Object> mapChild = new ConcurrentHashMap<String, Object>();

    public static void setAllRouteTag(String tag){
        ALL_ROUTE_TAG.set(tag);
    }

    public String getTagOfThread() {
        String t = tagOfThread.get();
        //如果整个线程内有多个RouteFactoryBean的实例，但是多个实例统一用一个Tag时，可在线程的开始时设置ALL_ROUTE_TAG.set(具体的Tag字符串)作为多个实例的默认Tag
        if(!StringUtils.hasText(t)){
            t=ALL_ROUTE_TAG.get();
        }
        return t;
    }

    public void setTagOfThread(String t){
        tagOfThread.set(t);
    }

    public DynamicFactoryBean(ApplicationContext applicationContext, Class<? extends TagAware> clz){
//        DefaultListableBeanFactory fty = (DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();
//        System.out.println(Arrays.asList(fty.getBeanDefinitionNames()));
        ac = applicationContext;
        parentClz= clz;
        Map<String, Object> mapChildBean = applicationContext.getBeansOfType(parentClz);
        System.out.println(mapChildBean);
        for(String name:mapChildBean.keySet()){
            Tag tag = applicationContext.findAnnotationOnBean(name, Tag.class);
            if(tag!=null && StringUtils.hasText(tag.value())){
                mapChild.put(tag.value(), mapChildBean.get(name));
            }
        }
        System.out.println(mapChild);
//        for (Identity dynamic: mapChildBean.values()){
//            System.out.println(dynamic.getTag());
//            mapChild.put(dynamic.getTag(), dynamic);
//        }
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
            if(tag==null || !StringUtils.hasText(tag.value())){
                continue;
            }
            if(StringUtils.hasText(childTag) && !tag.equals(childTag)){
                continue;
            }
            mapChild.put(tag.value(), mapChildBean.get(name));
            n++;
        }

//        for (TagContext identity: mapChildBean.values()){
//            System.out.println(identity.getClass());
//            System.out.println(identity instanceof Identity);
//            System.out.println(Proxy.isProxyClass(identity.getClass()));
//
//            if (childBean!=null&&!childBean.getTag().equals(identity.getTag())) {
//                continue;
//            }
//            if (Proxy.isProxyClass(identity.getClass())) {
//                continue;
//            }
//            if(identity instanceof Identity) {
//                mapChild.put(identity.getTag(), identity);
//            }
//            n++;
//        }
        return n;
    }

    @Override
    public T getObject() throws Exception {
        InvocationHandler invocationHandler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if("setTagOfContext".equals(method.getName())&&args!=null&&args[0]!=null){
                    setTagOfThread((String)args[0]);
                    return null;
                }
                if("getTagOfContext".equals(method.getName())){
                    return getTagOfThread();
                }
                Object obj = mapChild.get(getTagOfThread());
                if (obj==null){
                    return null;
                }
                try {
                    return method.invoke(obj, args);
                }catch (Exception e){
                    throw e.getCause();
                }

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
