package com.oszero.deliver.server.model.app.call;

import lombok.*;

/**
 * 阿里云电话 APP 配置
 *
 * @author oszero
 * @version 1.0.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AliYunCallApp extends CallApp {
    private String accessKeyId;
    private String accessKeySecret;

}
