package com.oszero.deliver.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Deliver 消息推送平台服务端启动类
 *
 * @author oszero
 * @version 1.0.0
 */
@SpringBootApplication
@EnableCaching //开启缓存功能
@EnableAsync // 开启异步功能
public class DeliverServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DeliverServerApplication.class);
    }
}
