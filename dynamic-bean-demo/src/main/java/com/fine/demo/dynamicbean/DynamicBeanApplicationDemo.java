package com.fine.demo.dynamicbean;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
//@ComponentScan({"com.fine.demo.dynamicbean"})
public class DynamicBeanApplicationDemo {

	public static void main(String[] args) {
		SpringApplication.run(DynamicBeanApplicationDemo.class, args);
	}

}
