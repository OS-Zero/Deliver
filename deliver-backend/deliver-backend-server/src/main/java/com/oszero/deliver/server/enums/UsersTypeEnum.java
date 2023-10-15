package com.oszero.deliver.server.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public enum UsersTypeEnum {
    COMPANY_ACCOUNT(1, "企业账号"),
    PHONE(2, "电话"),
    MAIL(3, "邮箱"),
    PLATFORM_USER_ID(4, "平台userId");
    private final Integer type;
    private final String name;

    public static UsersTypeEnum getByType(Integer type) {
        for (UsersTypeEnum v : values()) {
            if (v.getType().equals(type)) {
                return v;
            }
        }
        return null;
    }

    public static String getNameByType(Integer type) {
        for (UsersTypeEnum v : values()) {
            if (v.getType().equals(type)) {
                return v.getName();
            }
        }
        return null;
    }
}
