package com.oszero.deliver.server.config;

import com.oszero.deliver.server.interceptor.TraceInterceptor;
import com.oszero.deliver.server.trace.strategy.UUIDStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author oszero
 * @version 1.0.0
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
         registry.addInterceptor(new TraceInterceptor(new UUIDStrategy()));
    }
}
