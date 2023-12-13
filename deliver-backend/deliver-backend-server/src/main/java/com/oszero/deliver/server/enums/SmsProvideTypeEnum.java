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
public enum SmsProvideTypeEnum {

    aliyun(0,"aliyun"),
    tencent(1,"tencent");

    private final Integer code;
    private final String message;


    public static SmsProvideTypeEnum getInstanceByCode(Integer code) {
        for (SmsProvideTypeEnum item : values()
        ) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        return null;
    }

}
