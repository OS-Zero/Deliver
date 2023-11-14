package com.oszero.deliver.admin.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 消息类型枚举
 *
 * @author oszero
 * @version 1.0.0
 */
@Getter
@ToString
@AllArgsConstructor
public enum MessageTypeEnum {
    /**
     * 通用 text 消息 (电话、短信、邮件、钉钉、企微、飞书)
     */
    TEXT("1", "text", "text消息", -1),

    /**
     * 钉钉消息 4 代表钉钉渠道 1 代表序号
     */
    DING_IMAGE("4-1", "image", "钉钉图片消息", 4),
    DING_VOICE("4-2", "voice", "钉钉语音消息", 4),
    DING_FILE("4-3", "file", "钉钉文件消息", 4),
    DING_LINK("4-4", "link", "钉钉链接消息", 4),
    DING_OA("4-5", "oa", "钉钉OA消息", 4),
    DING_MARKDOWN("4-6", "markdown", "钉钉markdown消息", 4),
    DING_CARD("4-7", "action_card", "钉钉卡片消息", 4),

    // todo: 后续添加群聊、机器人消息

    /**
     * 企业微信消息 5 代表企业微信渠道 1 代表序号
     */
    WECHAT_IMAGE("5-1", "image", "企业微信图片消息", 5),
    WECHAT_VOICE("5-2", "voice", "企业微信语音消息", 5),
    WECHAT_VIDEO("5-3", "video", "企业微信视频消息", 5),
    WECHAT_FILE("5-4", "file", "企业微信文件消息", 5),
    WECHAT_TEXT_CARD("5-5", "textcard", "企业微信文本卡片消息", 5),
    WECHAT_MPNEWS("5-6", "mpnews", "企业微信图文消息（mpnews）", 5),
    WECHAT_MARKDOWN("5-7", "markdown", "企业微信markdown消息", 5),
    WECHAT_MINIPROGRAM_NOTICE("5-8", "miniprogram_notice", "企业微信小程序通知消息", 5),

    // todo: 后续添加群聊、机器人消息

    /**
     * 飞书消息 6 代表飞书渠道 1 代表序号
     */
    FEI_SHU_POST("6-1", "post", "富文本 post", 6),
    FEI_SHU_IMAGE("6-2", "image", "图片 image", 6),
    FEI_SHU_INTERACTIVE("6-3", "interactive", "消息卡片 interactive", 6),
    FEI_SHU_SHARE_CHAT("6-4", "share_chat", "分享群名片 share_chat", 6),
    FEI_SHU_SHARE_USER("6-5", "share_user", "分享个人名片 share_user", 6),
    FEI_SHU_AUDIO("6-6", "audio", "语音 audio", 6),
    FEI_SHU_MEDIA("6-7", "media", "视频 media", 6),
    FEI_SHU_FILE("6-8", "file", "文件 file", 6),
    FEI_SHU_STICKER("6-9", "sticker", "表情包 sticker", 6),

    // todo: 后续添加群聊、机器人、批量消息
    ;
    private final String code;
    private final String msgType;
    private final String name;
    private final Integer channelType;

    public static MessageTypeEnum getInstanceByCode(String code) {
        for (MessageTypeEnum v : values()) {
            if (v.getCode().equals(code)) {
                return v;
            }
        }
        return null;
    }
}
