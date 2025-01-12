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

package com.oszero.deliver.business.admin.constant;

import com.oszero.deliver.business.admin.constant.messageparam.*;
import com.oszero.deliver.business.common.enums.MessageTypeEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * @author oszero
 * @version 1.0.0
 */
public class MessageParamConstant {
    private static final Map<String, String> MESSAGE_PARAM_MAP = new HashMap<>();

    static {
        /********* 电话消息参数 **********/
        // 阿里云
        MESSAGE_PARAM_MAP.put(getKeyByCode(MessageTypeEnum.CALL_ALI_TEXT), CallMessageParam.ALI_PARAM);
        // 腾讯云
        MESSAGE_PARAM_MAP.put(getKeyByCode(MessageTypeEnum.CALL_TENCENT_TEXT), CallMessageParam.TENCENT_PARAM);
        /*********** 短信消息参数 **********/
        // 阿里云
        MESSAGE_PARAM_MAP.put(getKeyByCode(MessageTypeEnum.SMS_ALI_TEXT), SmsMessageParam.ALI_PARAM);
        // 腾讯云
        MESSAGE_PARAM_MAP.put(getKeyByCode(MessageTypeEnum.SMS_TENCENT_TEXT), SmsMessageParam.TENCENT_PARAM);
        /*********** 邮件消息参数 **********/
        MESSAGE_PARAM_MAP.put(getKeyByCode(MessageTypeEnum.EMAIL_TEXT), EmailMessageParam.EMAIL_PARAM);
        /*********** 钉钉消息参数 **********/
        // 钉钉工作通知消息
        MESSAGE_PARAM_MAP.put(getKeyByCode(MessageTypeEnum.DING_WORK_NOTICE_TEXT), DingMessageParam.WORK_NOTICE_TEXT_PARAM);
        MESSAGE_PARAM_MAP.put(getKeyByCode(MessageTypeEnum.DING_WORK_NOTICE_IMAGE), DingMessageParam.WORK_NOTICE_IMAGE_PARAM);
        MESSAGE_PARAM_MAP.put(getKeyByCode(MessageTypeEnum.DING_WORK_NOTICE_VOICE), DingMessageParam.WORK_NOTICE_VOICE_PARAM);
        MESSAGE_PARAM_MAP.put(getKeyByCode(MessageTypeEnum.DING_WORK_NOTICE_FILE), DingMessageParam.WORK_NOTICE_FILE_PARAM);
        MESSAGE_PARAM_MAP.put(getKeyByCode(MessageTypeEnum.DING_WORK_NOTICE_LINK), DingMessageParam.WORK_NOTICE_LINK_PARAM);
        MESSAGE_PARAM_MAP.put(getKeyByCode(MessageTypeEnum.DING_WORK_NOTICE_OA), DingMessageParam.WORK_NOTICE_OA_PARAM);
        MESSAGE_PARAM_MAP.put(getKeyByCode(MessageTypeEnum.DING_WORK_NOTICE_MARKDOWN), DingMessageParam.WORK_NOTICE_MARKDOWN_PARAM);
        MESSAGE_PARAM_MAP.put(getKeyByCode(MessageTypeEnum.DING_WORK_NOTICE_CARD), DingMessageParam.WORK_NOTICE_CARD_PARAM);
        // 钉钉机器人消息
        MESSAGE_PARAM_MAP.put(getKeyByCode(MessageTypeEnum.DING_ROBOT_TEXT), DingMessageParam.ROBOT_TEXT_PARAM);
        MESSAGE_PARAM_MAP.put(getKeyByCode(MessageTypeEnum.DING_ROBOT_MARKDOWN), DingMessageParam.ROBOT_MARKDOWN_PARAM);
        MESSAGE_PARAM_MAP.put(getKeyByCode(MessageTypeEnum.DING_ROBOT_IMAGE_MSG), DingMessageParam.ROBOT_IMAGE_MSG_PARAM);
        MESSAGE_PARAM_MAP.put(getKeyByCode(MessageTypeEnum.DING_ROBOT_LINK), DingMessageParam.ROBOT_LINK_PARAM);
        MESSAGE_PARAM_MAP.put(getKeyByCode(MessageTypeEnum.DING_ROBOT_ACTION_CARD), DingMessageParam.ROBOT_ACTION_CARD_PARAM);
        MESSAGE_PARAM_MAP.put(getKeyByCode(MessageTypeEnum.DING_ROBOT_ACTION_CARD_2), DingMessageParam.ROBOT_ACTION_CARD_2_PARAM);
        MESSAGE_PARAM_MAP.put(getKeyByCode(MessageTypeEnum.DING_ROBOT_ACTION_CARD_3), DingMessageParam.ROBOT_ACTION_CARD_3_PARAM);
        MESSAGE_PARAM_MAP.put(getKeyByCode(MessageTypeEnum.DING_ROBOT_ACTION_CARD_4), DingMessageParam.ROBOT_ACTION_CARD_4_PARAM);
        MESSAGE_PARAM_MAP.put(getKeyByCode(MessageTypeEnum.DING_ROBOT_ACTION_CARD_5), DingMessageParam.ROBOT_ACTION_CARD_5_PARAM);
        MESSAGE_PARAM_MAP.put(getKeyByCode(MessageTypeEnum.DING_ROBOT_ACTION_CARD_6), DingMessageParam.ROBOT_ACTION_CARD_6_PARAM);
        MESSAGE_PARAM_MAP.put(getKeyByCode(MessageTypeEnum.DING_ROBOT_AUDIO), DingMessageParam.ROBOT_AUDIO_PARAM);
        MESSAGE_PARAM_MAP.put(getKeyByCode(MessageTypeEnum.DING_ROBOT_FILE), DingMessageParam.ROBOT_FILE_PARAM);
        MESSAGE_PARAM_MAP.put(getKeyByCode(MessageTypeEnum.DING_ROBOT_VIDEO), DingMessageParam.ROBOT_VIDEO_PARAM);
        /*********** 企微消息参数 **********/
        // 企微应用消息
        MESSAGE_PARAM_MAP.put(getKeyByCode(MessageTypeEnum.WECHAT_APP_TEXT), WechatMessageParam.APP_TEXT_PARAM);
        MESSAGE_PARAM_MAP.put(getKeyByCode(MessageTypeEnum.WECHAT_APP_IMAGE), WechatMessageParam.APP_IMAGE_PARAM);
        MESSAGE_PARAM_MAP.put(getKeyByCode(MessageTypeEnum.WECHAT_APP_VOICE), WechatMessageParam.APP_VOICE_PARAM);
        MESSAGE_PARAM_MAP.put(getKeyByCode(MessageTypeEnum.WECHAT_APP_VIDEO), WechatMessageParam.APP_VIDEO_PARAM);
        MESSAGE_PARAM_MAP.put(getKeyByCode(MessageTypeEnum.WECHAT_APP_FILE), WechatMessageParam.APP_FILE_PARAM);
        MESSAGE_PARAM_MAP.put(getKeyByCode(MessageTypeEnum.WECHAT_APP_TEXT_CARD), WechatMessageParam.APP_TEXT_CARD_PARAM);
        MESSAGE_PARAM_MAP.put(getKeyByCode(MessageTypeEnum.WECHAT_APP_NEWS), WechatMessageParam.APP_NEWS_PARAM);
        MESSAGE_PARAM_MAP.put(getKeyByCode(MessageTypeEnum.WECHAT_APP_MPNEWS), WechatMessageParam.APP_MPNEWS_PARAM);
        MESSAGE_PARAM_MAP.put(getKeyByCode(MessageTypeEnum.WECHAT_APP_MARKDOWN), WechatMessageParam.APP_MARKDOWN_PARAM);
        MESSAGE_PARAM_MAP.put(getKeyByCode(MessageTypeEnum.WECHAT_APP_MINIPROGRAM_NOTICE), WechatMessageParam.APP_MINIPROGRAM_NOTICE_PARAM);
        MESSAGE_PARAM_MAP.put(getKeyByCode(MessageTypeEnum.WECHAT_APP_TEMPLATE_CARD), WechatMessageParam.APP_TEMPLATE_CARD_PARAM);
        // 企微应用消息到群聊会话
        MESSAGE_PARAM_MAP.put(getKeyByCode(MessageTypeEnum.WECHAT_APP_TO_GROUP_TEXT), WechatMessageParam.APP_TO_GROUP_TEXT_PARAM);
        MESSAGE_PARAM_MAP.put(getKeyByCode(MessageTypeEnum.WECHAT_APP_TO_GROUP_IMAGE), WechatMessageParam.APP_TO_GROUP_IMAGE_PARAM);
        MESSAGE_PARAM_MAP.put(getKeyByCode(MessageTypeEnum.WECHAT_APP_TO_GROUP_VOICE), WechatMessageParam.APP_TO_GROUP_VOICE_PARAM);
        MESSAGE_PARAM_MAP.put(getKeyByCode(MessageTypeEnum.WECHAT_APP_TO_GROUP_VIDEO), WechatMessageParam.APP_TO_GROUP_VIDEO_PARAM);
        MESSAGE_PARAM_MAP.put(getKeyByCode(MessageTypeEnum.WECHAT_APP_TO_GROUP_FILE), WechatMessageParam.APP_TO_GROUP_FILE_PARAM);
        MESSAGE_PARAM_MAP.put(getKeyByCode(MessageTypeEnum.WECHAT_APP_TO_GROUP_TEXT_CARD), WechatMessageParam.APP_TO_GROUP_TEXT_CARD_PARAM);
        MESSAGE_PARAM_MAP.put(getKeyByCode(MessageTypeEnum.WECHAT_APP_TO_GROUP_NEWS), WechatMessageParam.APP_TO_GROUP_NEWS_PARAM);
        MESSAGE_PARAM_MAP.put(getKeyByCode(MessageTypeEnum.WECHAT_APP_TO_GROUP_MPNEWS), WechatMessageParam.APP_TO_GROUP_MPNEWS_PARAM);
        MESSAGE_PARAM_MAP.put(getKeyByCode(MessageTypeEnum.WECHAT_APP_TO_GROUP_MARKDOWN), WechatMessageParam.APP_TO_GROUP_MARKDOWN_PARAM);
        // 企微家校消息
        MESSAGE_PARAM_MAP.put(getKeyByCode(MessageTypeEnum.WECHAT_SCHOOL_TEXT), WechatMessageParam.SCHOOL_TEXT_PARAM);
        MESSAGE_PARAM_MAP.put(getKeyByCode(MessageTypeEnum.WECHAT_SCHOOL_IMAGE), WechatMessageParam.SCHOOL_IMAGE_PARAM);
        MESSAGE_PARAM_MAP.put(getKeyByCode(MessageTypeEnum.WECHAT_SCHOOL_VOICE), WechatMessageParam.SCHOOL_VOICE_PARAM);
        MESSAGE_PARAM_MAP.put(getKeyByCode(MessageTypeEnum.WECHAT_SCHOOL_VIDEO), WechatMessageParam.SCHOOL_VIDEO_PARAM);
        MESSAGE_PARAM_MAP.put(getKeyByCode(MessageTypeEnum.WECHAT_SCHOOL_FILE), WechatMessageParam.SCHOOL_FILE_PARAM);
        MESSAGE_PARAM_MAP.put(getKeyByCode(MessageTypeEnum.WECHAT_SCHOOL_NEWS), WechatMessageParam.SCHOOL_NEWS_PARAM);
        MESSAGE_PARAM_MAP.put(getKeyByCode(MessageTypeEnum.WECHAT_SCHOOL_MPNEWS), WechatMessageParam.SCHOOL_MPNEWS_PARAM);
        MESSAGE_PARAM_MAP.put(getKeyByCode(MessageTypeEnum.WECHAT_SCHOOL_MINIPROGRAM_NOTICE), WechatMessageParam.SCHOOL_MINIPROGRAM_NOTICE_PARAM);
        // 企微群机器人消息
        MESSAGE_PARAM_MAP.put(getKeyByCode(MessageTypeEnum.WECHAT_GROUP_ROBOT_TEXT), WechatMessageParam.GROUP_ROBOT_TEXT_PARAM);
        MESSAGE_PARAM_MAP.put(getKeyByCode(MessageTypeEnum.WECHAT_GROUP_ROBOT_MARKDOWN), WechatMessageParam.GROUP_ROBOT_MARKDOWN_PARAM);
        MESSAGE_PARAM_MAP.put(getKeyByCode(MessageTypeEnum.WECHAT_GROUP_ROBOT_IMAGE), WechatMessageParam.GROUP_ROBOT_IMAGE_PARAM);
        MESSAGE_PARAM_MAP.put(getKeyByCode(MessageTypeEnum.WECHAT_GROUP_ROBOT_MPNEWS), WechatMessageParam.GROUP_ROBOT_MPNEWS_PARAM);
        MESSAGE_PARAM_MAP.put(getKeyByCode(MessageTypeEnum.WECHAT_GROUP_ROBOT_FILE), WechatMessageParam.GROUP_ROBOT_FILE_PARAM);
        MESSAGE_PARAM_MAP.put(getKeyByCode(MessageTypeEnum.WECHAT_GROUP_ROBOT_VOICE), WechatMessageParam.GROUP_ROBOT_VOICE_PARAM);
        MESSAGE_PARAM_MAP.put(getKeyByCode(MessageTypeEnum.WECHAT_GROUP_ROBOT_TEMPLATE_CARD), WechatMessageParam.GROUP_ROBOT_TEMPLATE_CARD_PARAM);
        /*********** 飞书消息参数 **********/
        MESSAGE_PARAM_MAP.put(getKeyByCode(MessageTypeEnum.FEI_SHU_TEXT), FeiShuMessageParam.TEXT_PARAM);
        MESSAGE_PARAM_MAP.put(getKeyByCode(MessageTypeEnum.FEI_SHU_POST), FeiShuMessageParam.POST_PARAM);
        MESSAGE_PARAM_MAP.put(getKeyByCode(MessageTypeEnum.FEI_SHU_IMAGE), FeiShuMessageParam.IMAGE_PARAM);
        MESSAGE_PARAM_MAP.put(getKeyByCode(MessageTypeEnum.FEI_SHU_INTERACTIVE), FeiShuMessageParam.INTERACTIVE_PARAM);
        MESSAGE_PARAM_MAP.put(getKeyByCode(MessageTypeEnum.FEI_SHU_SHARE_CHAT), FeiShuMessageParam.SHARE_CHAT_PARAM);
        MESSAGE_PARAM_MAP.put(getKeyByCode(MessageTypeEnum.FEI_SHU_SHARE_USER), FeiShuMessageParam.SHARE_USER_PARAM);
        MESSAGE_PARAM_MAP.put(getKeyByCode(MessageTypeEnum.FEI_SHU_AUDIO), FeiShuMessageParam.AUDIO_PARAM);
        MESSAGE_PARAM_MAP.put(getKeyByCode(MessageTypeEnum.FEI_SHU_MEDIA), FeiShuMessageParam.MEDIA_PARAM);
        MESSAGE_PARAM_MAP.put(getKeyByCode(MessageTypeEnum.FEI_SHU_FILE), FeiShuMessageParam.FILE_PARAM);
        MESSAGE_PARAM_MAP.put(getKeyByCode(MessageTypeEnum.FEI_SHU_STICKER), FeiShuMessageParam.STICKER_PARAM);
    }

    private static String getKeyByCode(MessageTypeEnum messageTypeEnum) {
        return messageTypeEnum.getCode();
    }

    public static String getMessageParamJsonConfig(String messageTypeEnumCode) {
        return MESSAGE_PARAM_MAP.getOrDefault(messageTypeEnumCode, "{}");
    }
}
