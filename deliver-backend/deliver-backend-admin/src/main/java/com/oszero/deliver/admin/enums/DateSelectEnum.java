package com.oszero.deliver.admin.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 日期选择枚举
 *
 * @author oszero
 * @version 1.0.0
 */
@Getter
@ToString
@AllArgsConstructor
public enum DateSelectEnum {

    DAY(1, "今日"),
    WEEK(2, "本周"),
    MONTH(3, "本月"),
    YEAR(4, "今年");
    private final Integer code;
    private final String name;

    /**
     * 通过 code 获取实例
     *
     * @param code code 码
     * @return 实例
     */
    public static DateSelectEnum getInstanceByCode(Integer code) {
        for (DateSelectEnum v : values()) {
            if (v.getCode().equals(code)) {
                return v;
            }
        }
        return null;
    }
}
