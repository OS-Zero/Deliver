package com.oszero.deliver.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.oszero.deliver.server.web.mapper")
public class DeliverServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DeliverServerApplication.class);
    }
}
