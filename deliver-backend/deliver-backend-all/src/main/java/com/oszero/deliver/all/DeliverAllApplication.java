package com.oszero.deliver.all;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 集合服务端与管理端
 *
 * @author oszero
 * @version 1.0.0
 */
@SpringBootApplication
@ComponentScan(value = {"com.oszero.deliver"})
public class DeliverAllApplication {
    public static void main(String[] args) {
        SpringApplication.run(DeliverAllApplication.class, args);
    }
}
