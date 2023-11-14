package com.oszero.deliver.admin.model.app;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 企业微信 APP 配置
 *
 * @author oszero
 * @version 1.0.0
 */
@Data
@Builder
//@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class WeChatApp extends AppConfig {
}
