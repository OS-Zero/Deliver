package com.oszero.deliver.business.common.enums;

import cn.hutool.core.util.StrUtil;
import com.oszero.deliver.business.common.constant.CommonConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author oszero
 * @version 1.0.0
 */
@Getter
@AllArgsConstructor
public enum PushSubjectEnum {
    CALL(newIndex(), "电话消息"),
    SMS(newIndex(), "短信消息"),
    EMAIL(newIndex(), "邮件消息"),
    DING_WORK_NOTICE(newIndex(), "钉钉工作通知"),
    DING_ROBOT(newIndex(), "钉钉机器人消息"),
    WECHAT_APP(newIndex(), "企微应用消息"),
    WECHAT_APP_TO_GROUP(newIndex(), "企微应用消息到群聊会话"),
    WECHAT_SCHOOL(newIndex(), "企微家校消息"),
    WECHAT_GROUP_ROBOT(newIndex(), "企微群机器人消息"),
    FEI_SHU(newIndex(), "飞书消息"),
    OFFICIAL_ACCOUNT_TEMPLATE(newIndex(), "微信公众号模板消息")
    ;
    private static int index = 0;

    private final String code;
    private final String name;

    public static final int MIN_INDEX = CommonConstant.MIN_INDEX;
    public static final int MAX_INDEX = 11;

    public static PushSubjectEnum getInstanceByCode(Integer code) {
        for (PushSubjectEnum value : values()) {
            if (StrUtil.equals(value.code, String.valueOf(code))) {
                return value;
            }
        }
        return null;
    }

    public static String newIndex() {
        return String.valueOf(++index);
    }
}
