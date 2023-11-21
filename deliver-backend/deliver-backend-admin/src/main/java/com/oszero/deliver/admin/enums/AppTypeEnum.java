package com.oszero.deliver.admin.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * APP 类型枚举
 *
 * @author oszero
 * @version 1.0.0
 */
@Getter
@ToString
@AllArgsConstructor
public enum AppTypeEnum {

    DING(1, "钉钉"),
    WECHAT(2, "企业微信"),
    FEI_SHU(3, "飞书");

    private final Integer code;
    private final String name;

    /**
     * 通过 code 获取实例
     *
     * @param code code 码
     * @return 实例
     */
    public static AppTypeEnum getInstanceByCode(Integer code) {
        for (AppTypeEnum v : values()) {
            if (v.getCode().equals(code)) {
                return v;
            }
        }
        return null;
    }
}
