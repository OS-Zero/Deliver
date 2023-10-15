package com.oszero.deliver.server.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public enum PushRangeEnum {
    /**
     * 不限
     */
    ALL(0,"不限"),
    /**
     * 企业内部
     */
    INNER(1,"企业内部"),

    /**
     * 外部用户
     */
    EXTERNAL(3,"外部用户");

    private final Integer code;
    private final String name;

    public static PushRangeEnum findByCode(Integer code){
        for (PushRangeEnum item: values()
             ) {
            if (item.getCode().equals(code)){
                return item;
            }
        }
        return null;
    }

    public static String getNameByCode(Integer code){
        for (PushRangeEnum item: values() ) {
            if (item.getCode().equals(code)){
                return item.getName();
            }
        }
        return null;
    }
}
