package com.oszero.deliver.server.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public enum MessageTypeEnum {
    TEXT(1,"text" ,"文本"),
    DING_LINK(2, "link","钉钉链接"),
    DING_CARD(3, "action_card","钉钉卡片"),
    DING_OA(4, "oa","钉钉oa"),
    WX_CARD(5,null, "微信卡片"),
    WX_MD(6,null, "微信MarkDown"),
    ;
    private final Integer type;
    private final String dingDingWorkType;
    private final String name;

    public static String getNameByType(Integer type) {
        for(MessageTypeEnum v : values()) {
            if(v.getType().equals(type)) {
                return v.getName();
            }
        }
        return null;
    }
}
