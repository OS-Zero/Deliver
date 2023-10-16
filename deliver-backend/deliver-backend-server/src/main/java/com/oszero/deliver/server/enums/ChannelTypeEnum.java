package com.oszero.deliver.server.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

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

    public static ChannelTypeEnum getInstanceByCode(Integer code) {
        for (ChannelTypeEnum v : values()) {
            if (v.getCode().equals(code)) {
                return v;
            }
        }
        return null;
    }
}
