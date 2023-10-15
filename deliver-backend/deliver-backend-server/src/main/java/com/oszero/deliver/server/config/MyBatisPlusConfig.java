package com.oszero.deliver.server.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.oszero.deliver.server.web.mapper")
public class MyBatisPlusConfig {
}
