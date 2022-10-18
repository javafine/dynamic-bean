package com.fine.demo.dynamicbean.controller;

import com.fine.demo.dynamicbean.bo.color.Color;
import com.fine.demo.dynamicbean.bo.shape.Shape;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: javafine
 * @Description:
 * @Date: Created in 9:52 2022/10/10
 */
@RestController
public class SampleController {

    @Autowired
    private Color color;
    @Autowired
    private Shape shape;

    @RequestMapping("/color/{c}")
    public @ResponseBody String color(@PathVariable("c") String c) {
        color.setTagOfContext(c);
        return color.printColor();
    }

    @RequestMapping("/color/{c}/shape/{s}")
    public @ResponseBody String colorAndShape(@PathVariable("c") String c, @PathVariable("s") String s) {
        color.setTagOfContext(c);
        shape.setTagOfContext(s);
        shape.setColor(color);
        return shape.printShape();
    }
}
