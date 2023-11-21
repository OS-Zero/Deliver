package com.oszero.deliver.admin.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 推送范围枚举
 *
 * @author oszero
 * @version 1.0.0
 */
@Getter
@ToString
@AllArgsConstructor
public enum PushRangeEnum {
    /**
     * 不限
     */
    ALL(0, "不限"),
    /**
     * 企业内部
     */
    INNER(1, "企业内部"),

    /**
     * 外部用户
     */
    EXTERNAL(2, "外部用户");

    private final Integer code;
    private final String name;

    /**
     * 通过 code 获取实例
     *
     * @param code code 码
     * @return 实例
     */
    public static PushRangeEnum getInstanceByCode(Integer code) {
        for (PushRangeEnum item : values()
        ) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        return null;
    }
}
