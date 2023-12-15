package com.oszero.deliver.server.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 电话提供商类型枚举
 *
 * @author oszero
 * @version 1.0.0
 */
@Getter
@ToString
@AllArgsConstructor
public enum CallProviderTypeEnum {
    ALI_YUN(1, "aliyun"),
    TENCENT(2, "tencent");

    private final Integer code;
    private final String name;

    public static CallProviderTypeEnum getInstanceByCode(Integer code) {
        for (CallProviderTypeEnum item : values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        return null;
    }
}
