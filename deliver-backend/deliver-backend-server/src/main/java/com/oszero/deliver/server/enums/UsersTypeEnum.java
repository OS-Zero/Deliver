package com.oszero.deliver.server.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public enum UsersTypeEnum {

    PHONE(1, "电话"),
    MAIL(2, "邮箱"),
    COMPANY_ACCOUNT(3, "企业账号"),
    PLATFORM_USER_ID(4, "平台userId");

    private final Integer code;
    private final String name;

    public static UsersTypeEnum getInstanceByCode(Integer code) {
        for (UsersTypeEnum v : values()) {
            if (v.getCode().equals(code)) {
                return v;
            }
        }
        return null;
    }
}
