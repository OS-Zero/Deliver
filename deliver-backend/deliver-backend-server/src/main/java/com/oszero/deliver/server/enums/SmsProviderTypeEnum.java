package com.oszero.deliver.server.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 短信服务提供商枚举
 *
 * @author oszero
 * @version 1.0.0
 */
@Getter
@ToString
@AllArgsConstructor
public enum SmsProviderTypeEnum {

    ALI_YUN(1, "aliyun"),
    TENCENT(2, "tencent");

    private final Integer code;
    private final String name;

    public static SmsProviderTypeEnum getInstanceByCode(Integer code) {
        for (SmsProviderTypeEnum item : values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        return null;
    }

}
