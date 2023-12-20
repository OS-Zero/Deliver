/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
    TEXT("1", "text", "Text 消息", -1),

    /**
     * 钉钉消息 4 代表钉钉渠道 1 代表序号
     */
    DING_IMAGE("4-1", "image", "钉钉-图片消息", 4),
    DING_VOICE("4-2", "voice", "钉钉-语音消息", 4),
    DING_FILE("4-3", "file", "钉钉-文件消息", 4),
    DING_LINK("4-4", "link", "钉钉-链接消息", 4),
    DING_OA("4-5", "oa", "钉钉-OA 消息", 4),
    DING_MARKDOWN("4-6", "markdown", "钉钉-markdown 消息", 4),
    DING_CARD("4-7", "action_card", "钉钉-卡片消息", 4),

    // todo: 后续添加群聊、机器人消息

    /**
     * 企业微信消息 5 代表企业微信渠道 1 代表序号
     */
    WECHAT_IMAGE("5-1", "image", "企业微信-图片消息", 5),
    WECHAT_VOICE("5-2", "voice", "企业微信-语音消息", 5),
    WECHAT_VIDEO("5-3", "video", "企业微信-视频消息", 5),
    WECHAT_FILE("5-4", "file", "企业微信-文件消息", 5),
    WECHAT_TEXT_CARD("5-5", "textcard", "企业微信-文本卡片消息", 5),
    WECHAT_MPNEWS("5-6", "mpnews", "企业微信-图文消息(mpnews)", 5),
    WECHAT_MARKDOWN("5-7", "markdown", "企业微信-markdown 消息", 5),
    WECHAT_MINIPROGRAM_NOTICE("5-8", "miniprogram_notice", "企业微信-小程序通知消息", 5),

    // todo: 后续添加群聊、机器人消息

    /**
     * 飞书消息 6 代表飞书渠道 1 代表序号
     */
    FEI_SHU_POST("6-1", "post", "飞书-富文本 post", 6),
    FEI_SHU_IMAGE("6-2", "image", "飞书-图片 image", 6),
    FEI_SHU_INTERACTIVE("6-3", "interactive", "飞书-消息卡片 interactive", 6),
    FEI_SHU_SHARE_CHAT("6-4", "share_chat", "飞书-分享群名片 share_chat", 6),
    FEI_SHU_SHARE_USER("6-5", "share_user", "飞书-分享个人名片 share_user", 6),
    FEI_SHU_AUDIO("6-6", "audio", "飞书-语音 audio", 6),
    FEI_SHU_MEDIA("6-7", "media", "飞书-视频 media", 6),
    FEI_SHU_FILE("6-8", "file", "飞书-文件 file", 6),
    FEI_SHU_STICKER("6-9", "sticker", "飞书-表情包 sticker", 6),
    ;
    private final String code;
    private final String msgType;
    private final String name;
    private final Integer channelType;

    /**
     * 通过 code 获取实例
     *
     * @param code code 码
     * @return 实例
     */
    public static MessageTypeEnum getInstanceByCode(String code) {
        for (MessageTypeEnum v : values()) {
            if (v.getCode().equals(code)) {
                return v;
            }
        }
        return null;
    }
}
