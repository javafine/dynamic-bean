package com.fine.demo.dynamicbean.bo.shape;

import com.fine.demo.dynamicbean.bo.color.Color;
import com.fine.dynamic.bean.Tag;

/**
 * @Author: FanBo
 * @Description:
 * @Date: Created in 10:58 2022/10/10
 */
@Tag("rectangle")
public class Rectangle implements Shape {
    private Color color;

    @Override
    public String printShape() {
        return "I am a rectangle, "+color.printColor();
    }

    @Override
    public void setColor(Color c) {
        color = c;
    }
}
