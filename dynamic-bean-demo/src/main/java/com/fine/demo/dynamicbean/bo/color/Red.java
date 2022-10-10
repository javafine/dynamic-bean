package com.fine.demo.dynamicbean.bo.color;

import com.fine.dynamic.bean.Tag;
import org.springframework.stereotype.Component;

/**
 * @Author: FanBo
 * @Description:
 * @Date: Created in 11:02 2022/10/10
 */
@Tag("red")
public class Red implements Color{

    @Override
    public String printColor() {
        return "I am red";
    }
}
