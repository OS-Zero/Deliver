package com.oszero.deliver.server.trace.strategy;

import cn.hutool.core.lang.UUID;
import com.oszero.deliver.server.trace.TraceStrategy;
import org.springframework.stereotype.Component;

/**
 * @author oszero
 * @version 1.0.0
 */
public class UUIDStrategy implements TraceStrategy {
    @Override
    public String createTrace() {
        return UUID.randomUUID().toString();
    }
}
