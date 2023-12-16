package com.oszero.deliver.server.util;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

/**
 * 事件工具类
 *
 * @author oszero
 * @version 1.0.0
 */
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(value = "mq-type", havingValue = "none")
public class ApplicationEventUtils {

    private final ApplicationContext applicationContext;

    public void publishCustomEvent(ApplicationEvent applicationEvent) {
        // 发布事件
        applicationContext.publishEvent(applicationEvent);
    }
}
