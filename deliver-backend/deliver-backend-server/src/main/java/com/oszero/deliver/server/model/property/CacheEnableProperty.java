package com.oszero.deliver.server.model.property;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 是否启用缓存
 *
 * @author oszero
 * @version 1.0.0
 */
@Getter
@Component
public class CacheEnableProperty {
    @Value("${cache-enable}")
    private String enable;
}

