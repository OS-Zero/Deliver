package com.oszero.deliver.server.model.app;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.*;

/**
 * 钉钉 APP 配置
 *
 * @author oszero
 * @version 1.0.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DingApp extends AppConfig{

    private Long agentId;
    private String appKey;
    private String appSecret;
}
