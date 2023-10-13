package com.oszero.deliver.server.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public enum UsersTypeEnum {
    AMS(1, "企业账号"),
    PHONE(2, "电话"),
    PLATFORM(3, "平台id"),
    EMAIL(4, "邮件"),
    ;
    private final Integer type;
    private final String name;

    public static String getNameByType(Integer type) {
        for(UsersTypeEnum v : values()) {
            if(v.getType().equals(type)) {
                return v.getName();
            }
        }
        return null;
    }
}
