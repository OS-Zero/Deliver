package com.oszero.deliver.server.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * Mybatis-Plus 配置
 *
 * @author oszero
 * @version 1.0.0
 */
@Configuration
@MapperScan("com.oszero.deliver.server.web.mapper")
public class MyBatisPlusConfig {
}
