package com.oszero.deliver.server.model.app.sms;

import lombok.*;

/**
 * 阿里云短信 APP 配置
 *
 * @author oszero
 * @version 1.0.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AliYunSmsApp extends SmsApp {

    private String signName;
    private String templateCode;

}
