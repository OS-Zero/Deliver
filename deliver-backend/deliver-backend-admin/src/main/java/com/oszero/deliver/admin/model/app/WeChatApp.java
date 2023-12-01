package com.oszero.deliver.admin.model.app;

import lombok.*;

/**
 * 企业微信 APP 配置
 *
 * @author oszero
 * @version 1.0.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class WeChatApp extends AppConfig {
    private String corpid;
    private String corpsecret;
    private String agentid;
}
