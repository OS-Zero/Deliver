package com.oszero.deliver.admin.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 渠道类型枚举
 *
 * @author oszero
 * @version 1.0.0
 */
@Getter
@ToString
@AllArgsConstructor
public enum ChannelTypeEnum {

    CALL(1, "电话"),
    SMS(2, "短信"),
    MAIL(3, "邮件"),
    DING(4, "钉钉"),
    WECHAT(5, "企微"),
    FEI_SHU(6, "飞书");
    private final Integer code;
    private final String name;

    /**
     * 通过 code 获取实例
     *
     * @param code code 码
     * @return 实例
     */
    public static ChannelTypeEnum getInstanceByCode(Integer code) {
        for (ChannelTypeEnum v : values()) {
            if (v.getCode().equals(code)) {
                return v;
            }
        }
        return null;
    }
}
