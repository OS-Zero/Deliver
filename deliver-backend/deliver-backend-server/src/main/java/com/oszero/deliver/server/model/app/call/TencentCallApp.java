package com.oszero.deliver.server.model.app.call;

import lombok.*;

/**
 * 腾讯云电话 APP 配置
 *
 * @author oszero
 * @version 1.0.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TencentCallApp extends CallApp{
    private String secretId;
    private String secretKey;
}
