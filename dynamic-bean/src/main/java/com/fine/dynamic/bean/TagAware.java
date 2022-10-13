package com.fine.dynamic.bean;

/**
 * @Author: javafine
 * @Description:
 * @Date: Created in 20:23 2022/2/21
 */
public interface TagAware {
    default void setTagOfContext(String tag) {}
    default String getTagOfContext(){return null;}
    default String getTagOfConcrete(){
        return this.getClass().getAnnotation(Tag.class).value();
    }
}
