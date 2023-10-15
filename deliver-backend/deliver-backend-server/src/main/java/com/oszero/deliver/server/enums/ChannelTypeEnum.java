package com.oszero.deliver.server.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public enum ChannelTypeEnum {

    CALL(1, "打电话"),
    SMS(2, "发短信"),
    MAIL(3, "邮件"),
    WECHAT(4, "微信"),
    DING(5, "钉钉"),
    FEI_SHU(6, "飞书");
    private final Integer type;
    private final String name;

    public static ChannelTypeEnum getByType(Integer type) {
        for (ChannelTypeEnum v : values()) {
            if (v.getType().equals(type)) {
                return v;
            }
        }
        return null;
    }

    public static String getNameByType(Integer type) {
        for (ChannelTypeEnum v : values()) {
            if (v.getType().equals(type)) {
                return v.getName();
            }
        }
        return null;
    }
}
