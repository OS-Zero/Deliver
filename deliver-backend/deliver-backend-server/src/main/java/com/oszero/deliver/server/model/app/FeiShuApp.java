package com.oszero.deliver.server.model.app;

import lombok.*;

/**
 * 飞书 APP 配置
 *
 * @author oszero
 * @version 1.0.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class FeiShuApp extends AppConfig {
    private String appId;
    private String appSecret;
}
