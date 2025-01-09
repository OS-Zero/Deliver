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

package com.oszero.deliver.business.common.enums;

import cn.hutool.core.util.StrUtil;
import com.oszero.deliver.business.common.constant.CommonConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @author oszero
 * @version 1.0.0
 */
@Getter
@ToString
@AllArgsConstructor
public enum MessageTypeEnum {
    /**
     * 通用文本消息 (电话、短信、邮件、钉钉、企微、飞书)
     */
    COMMON_TEXT(CommonConstant.COMMON_MESSAGE_TYPE_CODE, "text", "文本消息", null),

    /**
     * 钉钉消息
     */
    DING_IMAGE(getMessageTypeEnumCode(ChannelTypeEnum.DING, newDingIndex()), "image", "钉钉-图片消息", ChannelTypeEnum.DING.getCode()),
    DING_VOICE(getMessageTypeEnumCode(ChannelTypeEnum.DING, newDingIndex()), "voice", "钉钉-语音消息", ChannelTypeEnum.DING.getCode()),
    DING_FILE(getMessageTypeEnumCode(ChannelTypeEnum.DING, newDingIndex()), "file", "钉钉-文件消息", ChannelTypeEnum.DING.getCode()),
    DING_LINK(getMessageTypeEnumCode(ChannelTypeEnum.DING, newDingIndex()), "link", "钉钉-链接消息", ChannelTypeEnum.DING.getCode()),
    DING_OA(getMessageTypeEnumCode(ChannelTypeEnum.DING, newDingIndex()), "oa", "钉钉-OA 消息", ChannelTypeEnum.DING.getCode()),
    DING_MARKDOWN(getMessageTypeEnumCode(ChannelTypeEnum.DING, newDingIndex()), "markdown", "钉钉-markdown 消息", ChannelTypeEnum.DING.getCode()),
    DING_CARD(getMessageTypeEnumCode(ChannelTypeEnum.DING, newDingIndex()), "action_card", "钉钉-卡片消息", ChannelTypeEnum.DING.getCode()),

    /**
     * 企业微信消息
     */
    WECHAT_IMAGE(getMessageTypeEnumCode(ChannelTypeEnum.WECHAT, newWechatIndex()), "image", "企业微信-图片消息", ChannelTypeEnum.WECHAT.getCode()),
    WECHAT_VOICE(getMessageTypeEnumCode(ChannelTypeEnum.WECHAT, newWechatIndex()), "voice", "企业微信-语音消息", ChannelTypeEnum.WECHAT.getCode()),
    WECHAT_VIDEO(getMessageTypeEnumCode(ChannelTypeEnum.WECHAT, newWechatIndex()), "video", "企业微信-视频消息", ChannelTypeEnum.WECHAT.getCode()),
    WECHAT_FILE(getMessageTypeEnumCode(ChannelTypeEnum.WECHAT, newWechatIndex()), "file", "企业微信-文件消息", ChannelTypeEnum.WECHAT.getCode()),
    WECHAT_TEXT_CARD(getMessageTypeEnumCode(ChannelTypeEnum.WECHAT, newWechatIndex()), "textcard", "企业微信-文本卡片消息", ChannelTypeEnum.WECHAT.getCode()),
    WECHAT_MPNEWS(getMessageTypeEnumCode(ChannelTypeEnum.WECHAT, newWechatIndex()), "mpnews", "企业微信-图文消息(mpnews)", ChannelTypeEnum.WECHAT.getCode()),
    WECHAT_MARKDOWN(getMessageTypeEnumCode(ChannelTypeEnum.WECHAT, newWechatIndex()), "markdown", "企业微信-markdown 消息", ChannelTypeEnum.WECHAT.getCode()),
    WECHAT_MINIPROGRAM_NOTICE(getMessageTypeEnumCode(ChannelTypeEnum.WECHAT, newWechatIndex()), "miniprogram_notice", "企业微信-小程序通知消息", ChannelTypeEnum.WECHAT.getCode()),

    /**
     * 飞书消息
     */
    FEI_SHU_POST(getMessageTypeEnumCode(ChannelTypeEnum.FEI_SHU, newFeiShuIndex()), "post", "飞书-富文本 post", ChannelTypeEnum.FEI_SHU.getCode()),
    FEI_SHU_IMAGE(getMessageTypeEnumCode(ChannelTypeEnum.FEI_SHU, newFeiShuIndex()), "image", "飞书-图片 image", ChannelTypeEnum.FEI_SHU.getCode()),
    FEI_SHU_INTERACTIVE(getMessageTypeEnumCode(ChannelTypeEnum.FEI_SHU, newFeiShuIndex()), "interactive", "飞书-消息卡片 interactive", ChannelTypeEnum.FEI_SHU.getCode()),
    FEI_SHU_SHARE_CHAT(getMessageTypeEnumCode(ChannelTypeEnum.FEI_SHU, newFeiShuIndex()), "share_chat", "飞书-分享群名片 share_chat", ChannelTypeEnum.FEI_SHU.getCode()),
    FEI_SHU_SHARE_USER(getMessageTypeEnumCode(ChannelTypeEnum.FEI_SHU, newFeiShuIndex()), "share_user", "飞书-分享个人名片 share_user", ChannelTypeEnum.FEI_SHU.getCode()),
    FEI_SHU_AUDIO(getMessageTypeEnumCode(ChannelTypeEnum.FEI_SHU, newFeiShuIndex()), "audio", "飞书-语音 audio", ChannelTypeEnum.FEI_SHU.getCode()),
    FEI_SHU_MEDIA(getMessageTypeEnumCode(ChannelTypeEnum.FEI_SHU, newFeiShuIndex()), "media", "飞书-视频 media", ChannelTypeEnum.FEI_SHU.getCode()),
    FEI_SHU_FILE(getMessageTypeEnumCode(ChannelTypeEnum.FEI_SHU, newFeiShuIndex()), "file", "飞书-文件 file", ChannelTypeEnum.FEI_SHU.getCode()),
    FEI_SHU_STICKER(getMessageTypeEnumCode(ChannelTypeEnum.FEI_SHU, newFeiShuIndex()), "sticker", "飞书-表情包 sticker", ChannelTypeEnum.FEI_SHU.getCode());

    private static int dingIndex = 0;
    private static int wechatIndex = 0;
    private static int feiShuIndex = 0;

    private static String newDingIndex() {
        return String.valueOf(++dingIndex);
    }

    private static String newWechatIndex() {
        return String.valueOf(++wechatIndex);
    }

    private static String newFeiShuIndex() {
        return String.valueOf(++feiShuIndex);
    }

    private static String getMessageTypeEnumCode(ChannelTypeEnum channelTypeEnum, String newIndex) {
        return channelTypeEnum.getCode() + CommonConstant.CODE_SEPARATE + newIndex;
    }

    private final String code;
    private final String msgType;
    private final String name;
    private final String channelType;

    public static MessageTypeEnum getInstanceByCode(String code) {
        for (MessageTypeEnum typeEnum : values()) {
            if (StrUtil.equals(typeEnum.getCode(), code)) {
                return typeEnum;
            }
        }
        return null;
    }
}
