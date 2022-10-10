package com.fine.demo.dynamicbean.bo.shape;

import com.fine.demo.dynamicbean.bo.color.Color;
import com.fine.dynamic.bean.TagAware;

/**
 * @Author: FanBo
 * @Description:
 * @Date: Created in 10:38 2022/10/10
 */
public interface Shape extends TagAware {
    String printShape();
    void setColor(Color c);
}
