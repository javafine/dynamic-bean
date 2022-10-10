package com.fine.demo.dynamicbean.bo.color;

import com.fine.dynamic.bean.Tag;
import org.springframework.stereotype.Component;

/**
 * @Author: FanBo
 * @Description:
 * @Date: Created in 11:04 2022/10/10
 */
//@Component
@Tag("blue")
public class Blue implements Color{
    @Override
    public String printColor() {
        return "I am blue";
    }
}
