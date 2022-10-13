package com.fine.demo.dynamicbean.bo.shape;

import com.fine.demo.dynamicbean.bo.color.Color;
import com.fine.dynamic.bean.Tag;

/**
 * @Author: javafine
 * @Description:
 * @Date: Created in 10:56 2022/10/10
 */
@Tag("circle")
public class Circle implements Shape {
    private Color color;

    @Override
    public String printShape() {
        return "I am a circle, "+color.printColor();
    }

    @Override
    public void setColor(Color c) {
        color = c;
    }
}
