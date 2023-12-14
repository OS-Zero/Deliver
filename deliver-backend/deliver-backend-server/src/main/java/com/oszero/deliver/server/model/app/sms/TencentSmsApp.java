package com.oszero.deliver.server.model.app.sms;

import lombok.*;

/**
 * 腾讯云短信 APP 配置
 *
 * @author oszero
 * @version 1.0.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TencentSmsApp extends SmsApp {
    private String secretId;
    private String secretKey;
}
