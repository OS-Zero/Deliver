package com.oszero.deliver.server.trace.strategy;

import cn.hutool.core.lang.UUID;
import com.oszero.deliver.server.trace.TraceIdStrategy;
import org.springframework.stereotype.Component;

/**
 * UUID 策略
 *
 * @author oszero
 * @version 1.0.0
 */
@Component
public class UUIDStrategy implements TraceIdStrategy {
    @Override
    public String createTraceId() {
        return UUID.randomUUID().toString();
    }
}
