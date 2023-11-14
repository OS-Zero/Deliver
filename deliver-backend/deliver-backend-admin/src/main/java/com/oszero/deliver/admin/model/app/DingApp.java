package com.oszero.deliver.admin.model.app;

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
