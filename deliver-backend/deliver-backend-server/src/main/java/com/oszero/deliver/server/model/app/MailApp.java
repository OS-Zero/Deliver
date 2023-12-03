package com.oszero.deliver.server.model.app;

import lombok.*;

/**
 * 邮箱 APP 配置
 *
 * @author oszero
 * @version 1.0.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MailApp extends AppConfig {
    private String host;
    private String username;
    private String password;
    // 默认值 true
    private String auth = "true";
    private String sslEnable = "true";
}
