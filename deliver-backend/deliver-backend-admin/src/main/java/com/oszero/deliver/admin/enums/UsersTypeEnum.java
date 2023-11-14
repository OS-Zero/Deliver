package com.oszero.deliver.admin.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 发送用户类型枚举
 *
 * @author oszero
 * @version 1.0.0
 */
@Getter
@ToString
@AllArgsConstructor
public enum UsersTypeEnum {

    COMPANY_ACCOUNT(1, "企业账号"),
    PHONE(2, "电话"),
    MAIL(3, "邮箱"),
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
