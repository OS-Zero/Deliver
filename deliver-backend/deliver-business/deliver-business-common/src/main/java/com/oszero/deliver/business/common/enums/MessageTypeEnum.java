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
    /******************** 电话消息 ********************/
    // 阿里云
    CALL_ALI_TEXT(getMessageTypeEnumCode(ChannelTypeEnum.CALL, ChannelProviderTypeEnum.ALI, newIndex()), "text", "阿里云电话-文本消息", ChannelTypeEnum.CALL.getCode(), ChannelProviderTypeEnum.ALI.getCode(), PushSubjectEnum.CALL.getCode()),
    // 腾讯云
    CALL_TENCENT_TEXT(getMessageTypeEnumCode(ChannelTypeEnum.CALL, ChannelProviderTypeEnum.TENCENT, newIndex()), "text", "腾讯云电话-文本消息", ChannelTypeEnum.CALL.getCode(), ChannelProviderTypeEnum.TENCENT.getCode(), PushSubjectEnum.CALL.getCode()),
    /******************** 短信消息 ********************/
    // 阿里云
    SMS_ALI_TEXT(getMessageTypeEnumCode(ChannelTypeEnum.SMS, ChannelProviderTypeEnum.ALI, newIndex()), "text", "阿里云短信-文本消息", ChannelTypeEnum.SMS.getCode(), ChannelProviderTypeEnum.ALI.getCode(), PushSubjectEnum.SMS.getCode()),
    // 腾讯云
    SMS_TENCENT_TEXT(getMessageTypeEnumCode(ChannelTypeEnum.SMS, ChannelProviderTypeEnum.TENCENT, newIndex()), "text", "腾讯云短信-文本消息", ChannelTypeEnum.SMS.getCode(), ChannelProviderTypeEnum.TENCENT.getCode(), PushSubjectEnum.SMS.getCode()),
    /******************** 邮件消息 ********************/
    EMAIL_TEXT(getMessageTypeEnumCode(ChannelTypeEnum.MAIL, ChannelProviderTypeEnum.DEFAULT, newIndex()), "text", "邮件-文本消息", ChannelTypeEnum.MAIL.getCode(), ChannelProviderTypeEnum.DEFAULT.getCode(), PushSubjectEnum.EMAIL.getCode()),
    /******************** 钉钉消息 ********************/
    // 钉钉工作通知
    DING_WORK_NOTICE_TEXT(getMessageTypeEnumCode(ChannelTypeEnum.DING, ChannelProviderTypeEnum.DEFAULT, newIndex()), "text", "钉钉工作通知-文本消息", ChannelTypeEnum.DING.getCode(), ChannelProviderTypeEnum.DEFAULT.getCode(), PushSubjectEnum.DING_WORK_NOTICE.getCode()),
    DING_WORK_NOTICE_IMAGE(getMessageTypeEnumCode(ChannelTypeEnum.DING, ChannelProviderTypeEnum.DEFAULT, newIndex()), "image", "钉钉工作通知-图片消息", ChannelTypeEnum.DING.getCode(), ChannelProviderTypeEnum.DEFAULT.getCode(), PushSubjectEnum.DING_WORK_NOTICE.getCode()),
    DING_WORK_NOTICE_VOICE(getMessageTypeEnumCode(ChannelTypeEnum.DING, ChannelProviderTypeEnum.DEFAULT, newIndex()), "voice", "钉钉工作通知-语音消息", ChannelTypeEnum.DING.getCode(), ChannelProviderTypeEnum.DEFAULT.getCode(), PushSubjectEnum.DING_WORK_NOTICE.getCode()),
    DING_WORK_NOTICE_FILE(getMessageTypeEnumCode(ChannelTypeEnum.DING, ChannelProviderTypeEnum.DEFAULT, newIndex()), "file", "钉钉工作通知-文件消息", ChannelTypeEnum.DING.getCode(), ChannelProviderTypeEnum.DEFAULT.getCode(), PushSubjectEnum.DING_WORK_NOTICE.getCode()),
    DING_WORK_NOTICE_LINK(getMessageTypeEnumCode(ChannelTypeEnum.DING, ChannelProviderTypeEnum.DEFAULT, newIndex()), "link", "钉钉工作通知-链接消息", ChannelTypeEnum.DING.getCode(), ChannelProviderTypeEnum.DEFAULT.getCode(), PushSubjectEnum.DING_WORK_NOTICE.getCode()),
    DING_WORK_NOTICE_OA(getMessageTypeEnumCode(ChannelTypeEnum.DING, ChannelProviderTypeEnum.DEFAULT, newIndex()), "oa", "钉钉工作通知-OA消息", ChannelTypeEnum.DING.getCode(), ChannelProviderTypeEnum.DEFAULT.getCode(), PushSubjectEnum.DING_WORK_NOTICE.getCode()),
    DING_WORK_NOTICE_MARKDOWN(getMessageTypeEnumCode(ChannelTypeEnum.DING, ChannelProviderTypeEnum.DEFAULT, newIndex()), "markdown", "钉钉工作通知-markdown消息", ChannelTypeEnum.DING.getCode(), ChannelProviderTypeEnum.DEFAULT.getCode(), PushSubjectEnum.DING_WORK_NOTICE.getCode()),
    DING_WORK_NOTICE_CARD(getMessageTypeEnumCode(ChannelTypeEnum.DING, ChannelProviderTypeEnum.DEFAULT, newIndex()), "action_card", "钉钉工作通知-卡片消息", ChannelTypeEnum.DING.getCode(), ChannelProviderTypeEnum.DEFAULT.getCode(), PushSubjectEnum.DING_WORK_NOTICE.getCode()),
    // 钉钉机器人消息
    DING_ROBOT_TEXT(getMessageTypeEnumCode(ChannelTypeEnum.DING, ChannelProviderTypeEnum.DEFAULT, newIndex()), "sampleText", "钉钉机器人-文本消息", ChannelTypeEnum.DING.getCode(), ChannelProviderTypeEnum.DEFAULT.getCode(), PushSubjectEnum.DING_ROBOT.getCode()),
    DING_ROBOT_MARKDOWN(getMessageTypeEnumCode(ChannelTypeEnum.DING, ChannelProviderTypeEnum.DEFAULT, newIndex()), "sampleMarkdown", "钉钉机器人-Markdown消息", ChannelTypeEnum.DING.getCode(), ChannelProviderTypeEnum.DEFAULT.getCode(), PushSubjectEnum.DING_ROBOT.getCode()),
    DING_ROBOT_IMAGE_MSG(getMessageTypeEnumCode(ChannelTypeEnum.DING, ChannelProviderTypeEnum.DEFAULT, newIndex()), "sampleImageMsg", "钉钉机器人-图片消息", ChannelTypeEnum.DING.getCode(), ChannelProviderTypeEnum.DEFAULT.getCode(), PushSubjectEnum.DING_ROBOT.getCode()),
    DING_ROBOT_LINK(getMessageTypeEnumCode(ChannelTypeEnum.DING, ChannelProviderTypeEnum.DEFAULT, newIndex()), "sampleLink", "钉钉机器人-链接消息", ChannelTypeEnum.DING.getCode(), ChannelProviderTypeEnum.DEFAULT.getCode(), PushSubjectEnum.DING_ROBOT.getCode()),
    DING_ROBOT_ACTION_CARD(getMessageTypeEnumCode(ChannelTypeEnum.DING, ChannelProviderTypeEnum.DEFAULT, newIndex()), "sampleActionCard", "钉钉机器人-ActionCard类型消息", ChannelTypeEnum.DING.getCode(), ChannelProviderTypeEnum.DEFAULT.getCode(), PushSubjectEnum.DING_ROBOT.getCode()),
    DING_ROBOT_ACTION_CARD_2(getMessageTypeEnumCode(ChannelTypeEnum.DING, ChannelProviderTypeEnum.DEFAULT, newIndex()), "sampleActionCard2", "钉钉机器人-ActionCard类型2消息", ChannelTypeEnum.DING.getCode(), ChannelProviderTypeEnum.DEFAULT.getCode(), PushSubjectEnum.DING_ROBOT.getCode()),
    DING_ROBOT_ACTION_CARD_3(getMessageTypeEnumCode(ChannelTypeEnum.DING, ChannelProviderTypeEnum.DEFAULT, newIndex()), "sampleActionCard3", "钉钉机器人-ActionCard类型3消息", ChannelTypeEnum.DING.getCode(), ChannelProviderTypeEnum.DEFAULT.getCode(), PushSubjectEnum.DING_ROBOT.getCode()),
    DING_ROBOT_ACTION_CARD_4(getMessageTypeEnumCode(ChannelTypeEnum.DING, ChannelProviderTypeEnum.DEFAULT, newIndex()), "sampleActionCard4", "钉钉机器人-ActionCard类型4消息", ChannelTypeEnum.DING.getCode(), ChannelProviderTypeEnum.DEFAULT.getCode(), PushSubjectEnum.DING_ROBOT.getCode()),
    DING_ROBOT_ACTION_CARD_5(getMessageTypeEnumCode(ChannelTypeEnum.DING, ChannelProviderTypeEnum.DEFAULT, newIndex()), "sampleActionCard5", "钉钉机器人-ActionCard类型5消息", ChannelTypeEnum.DING.getCode(), ChannelProviderTypeEnum.DEFAULT.getCode(), PushSubjectEnum.DING_ROBOT.getCode()),
    DING_ROBOT_ACTION_CARD_6(getMessageTypeEnumCode(ChannelTypeEnum.DING, ChannelProviderTypeEnum.DEFAULT, newIndex()), "sampleActionCard6", "钉钉机器人-ActionCard类型6消息", ChannelTypeEnum.DING.getCode(), ChannelProviderTypeEnum.DEFAULT.getCode(), PushSubjectEnum.DING_ROBOT.getCode()),
    DING_ROBOT_AUDIO(getMessageTypeEnumCode(ChannelTypeEnum.DING, ChannelProviderTypeEnum.DEFAULT, newIndex()), "sampleAudio", "钉钉机器人-语音消息", ChannelTypeEnum.DING.getCode(), ChannelProviderTypeEnum.DEFAULT.getCode(), PushSubjectEnum.DING_ROBOT.getCode()),
    DING_ROBOT_FILE(getMessageTypeEnumCode(ChannelTypeEnum.DING, ChannelProviderTypeEnum.DEFAULT, newIndex()), "sampleFile", "钉钉机器人-文件消息", ChannelTypeEnum.DING.getCode(), ChannelProviderTypeEnum.DEFAULT.getCode(), PushSubjectEnum.DING_ROBOT.getCode()),
    DING_ROBOT_VIDEO(getMessageTypeEnumCode(ChannelTypeEnum.DING, ChannelProviderTypeEnum.DEFAULT, newIndex()), "sampleVideo", "钉钉机器人-视频消息", ChannelTypeEnum.DING.getCode(), ChannelProviderTypeEnum.DEFAULT.getCode(), PushSubjectEnum.DING_ROBOT.getCode()),
    /******************** 企业微信消息 ********************/
    // 企业微信应用消息
    WECHAT_APP_TEXT(getMessageTypeEnumCode(ChannelTypeEnum.WECHAT, ChannelProviderTypeEnum.DEFAULT, newIndex()), "text", "企微应用-文本消息", ChannelTypeEnum.WECHAT.getCode(), ChannelProviderTypeEnum.DEFAULT.getCode(), PushSubjectEnum.WECHAT_APP.getCode()),
    WECHAT_APP_IMAGE(getMessageTypeEnumCode(ChannelTypeEnum.WECHAT, ChannelProviderTypeEnum.DEFAULT, newIndex()), "image", "企微应用-图片消息", ChannelTypeEnum.WECHAT.getCode(), ChannelProviderTypeEnum.DEFAULT.getCode(), PushSubjectEnum.WECHAT_APP.getCode()),
    WECHAT_APP_VOICE(getMessageTypeEnumCode(ChannelTypeEnum.WECHAT, ChannelProviderTypeEnum.DEFAULT, newIndex()), "voice", "企微应用-语音消息", ChannelTypeEnum.WECHAT.getCode(), ChannelProviderTypeEnum.DEFAULT.getCode(), PushSubjectEnum.WECHAT_APP.getCode()),
    WECHAT_APP_VIDEO(getMessageTypeEnumCode(ChannelTypeEnum.WECHAT, ChannelProviderTypeEnum.DEFAULT, newIndex()), "video", "企微应用-视频消息", ChannelTypeEnum.WECHAT.getCode(), ChannelProviderTypeEnum.DEFAULT.getCode(), PushSubjectEnum.WECHAT_APP.getCode()),
    WECHAT_APP_FILE(getMessageTypeEnumCode(ChannelTypeEnum.WECHAT, ChannelProviderTypeEnum.DEFAULT, newIndex()), "file", "企微应用-文件消息", ChannelTypeEnum.WECHAT.getCode(), ChannelProviderTypeEnum.DEFAULT.getCode(), PushSubjectEnum.WECHAT_APP.getCode()),
    WECHAT_APP_TEXT_CARD(getMessageTypeEnumCode(ChannelTypeEnum.WECHAT, ChannelProviderTypeEnum.DEFAULT, newIndex()), "textcard", "企微应用-文本卡片消息", ChannelTypeEnum.WECHAT.getCode(), ChannelProviderTypeEnum.DEFAULT.getCode(), PushSubjectEnum.WECHAT_APP.getCode()),
    WECHAT_APP_NEWS(getMessageTypeEnumCode(ChannelTypeEnum.WECHAT, ChannelProviderTypeEnum.DEFAULT, newIndex()), "news", "企微应用-图文消息", ChannelTypeEnum.WECHAT.getCode(), ChannelProviderTypeEnum.DEFAULT.getCode(), PushSubjectEnum.WECHAT_APP.getCode()),
    WECHAT_APP_MPNEWS(getMessageTypeEnumCode(ChannelTypeEnum.WECHAT, ChannelProviderTypeEnum.DEFAULT, newIndex()), "mpnews", "企微应用-图文消息(mpnews)", ChannelTypeEnum.WECHAT.getCode(), ChannelProviderTypeEnum.DEFAULT.getCode(), PushSubjectEnum.WECHAT_APP.getCode()),
    WECHAT_APP_MARKDOWN(getMessageTypeEnumCode(ChannelTypeEnum.WECHAT, ChannelProviderTypeEnum.DEFAULT, newIndex()), "markdown", "企微应用-markdown消息", ChannelTypeEnum.WECHAT.getCode(), ChannelProviderTypeEnum.DEFAULT.getCode(), PushSubjectEnum.WECHAT_APP.getCode()),
    WECHAT_APP_MINIPROGRAM_NOTICE(getMessageTypeEnumCode(ChannelTypeEnum.WECHAT, ChannelProviderTypeEnum.DEFAULT, newIndex()), "miniprogram_notice", "企微应用-小程序通知消息", ChannelTypeEnum.WECHAT.getCode(), ChannelProviderTypeEnum.DEFAULT.getCode(), PushSubjectEnum.WECHAT_APP.getCode()),
    WECHAT_APP_TEMPLATE_CARD(getMessageTypeEnumCode(ChannelTypeEnum.WECHAT, ChannelProviderTypeEnum.DEFAULT, newIndex()), "template_card", "企微应用-模板卡片消息", ChannelTypeEnum.WECHAT.getCode(), ChannelProviderTypeEnum.DEFAULT.getCode(), PushSubjectEnum.WECHAT_APP.getCode()),
    // 企微应用消息到群聊会话
    WECHAT_APP_TO_GROUP_TEXT(getMessageTypeEnumCode(ChannelTypeEnum.WECHAT, ChannelProviderTypeEnum.DEFAULT, newIndex()), "text", "企微应用消息到群聊会话-文本消息", ChannelTypeEnum.WECHAT.getCode(), ChannelProviderTypeEnum.DEFAULT.getCode(), PushSubjectEnum.WECHAT_APP_TO_GROUP.getCode()),
    WECHAT_APP_TO_GROUP_IMAGE(getMessageTypeEnumCode(ChannelTypeEnum.WECHAT, ChannelProviderTypeEnum.DEFAULT, newIndex()), "image", "企微应用消息到群聊会话-图片消息", ChannelTypeEnum.WECHAT.getCode(), ChannelProviderTypeEnum.DEFAULT.getCode(), PushSubjectEnum.WECHAT_APP_TO_GROUP.getCode()),
    WECHAT_APP_TO_GROUP_VOICE(getMessageTypeEnumCode(ChannelTypeEnum.WECHAT, ChannelProviderTypeEnum.DEFAULT, newIndex()), "voice", "企微应用消息到群聊会话-语音消息", ChannelTypeEnum.WECHAT.getCode(), ChannelProviderTypeEnum.DEFAULT.getCode(), PushSubjectEnum.WECHAT_APP_TO_GROUP.getCode()),
    WECHAT_APP_TO_GROUP_VIDEO(getMessageTypeEnumCode(ChannelTypeEnum.WECHAT, ChannelProviderTypeEnum.DEFAULT, newIndex()), "video", "企微应用消息到群聊会话-视频消息", ChannelTypeEnum.WECHAT.getCode(), ChannelProviderTypeEnum.DEFAULT.getCode(), PushSubjectEnum.WECHAT_APP_TO_GROUP.getCode()),
    WECHAT_APP_TO_GROUP_FILE(getMessageTypeEnumCode(ChannelTypeEnum.WECHAT, ChannelProviderTypeEnum.DEFAULT, newIndex()), "file", "企微应用消息到群聊会话-文件消息", ChannelTypeEnum.WECHAT.getCode(), ChannelProviderTypeEnum.DEFAULT.getCode(), PushSubjectEnum.WECHAT_APP_TO_GROUP.getCode()),
    WECHAT_APP_TO_GROUP_TEXT_CARD(getMessageTypeEnumCode(ChannelTypeEnum.WECHAT, ChannelProviderTypeEnum.DEFAULT, newIndex()), "textcard", "企微应用消息到群聊会话-文本卡片消息", ChannelTypeEnum.WECHAT.getCode(), ChannelProviderTypeEnum.DEFAULT.getCode(), PushSubjectEnum.WECHAT_APP_TO_GROUP.getCode()),
    WECHAT_APP_TO_GROUP_NEWS(getMessageTypeEnumCode(ChannelTypeEnum.WECHAT, ChannelProviderTypeEnum.DEFAULT, newIndex()), "news", "企微应用消息到群聊会话-图文消息", ChannelTypeEnum.WECHAT.getCode(), ChannelProviderTypeEnum.DEFAULT.getCode(), PushSubjectEnum.WECHAT_APP_TO_GROUP.getCode()),
    WECHAT_APP_TO_GROUP_MPNEWS(getMessageTypeEnumCode(ChannelTypeEnum.WECHAT, ChannelProviderTypeEnum.DEFAULT, newIndex()), "mpnews", "企微应用消息到群聊会话-图文消息(mpnews)", ChannelTypeEnum.WECHAT.getCode(), ChannelProviderTypeEnum.DEFAULT.getCode(), PushSubjectEnum.WECHAT_APP_TO_GROUP.getCode()),
    WECHAT_APP_TO_GROUP_MARKDOWN(getMessageTypeEnumCode(ChannelTypeEnum.WECHAT, ChannelProviderTypeEnum.DEFAULT, newIndex()), "markdown", "企微应用消息到群聊会话-markdown消息", ChannelTypeEnum.WECHAT.getCode(), ChannelProviderTypeEnum.DEFAULT.getCode(), PushSubjectEnum.WECHAT_APP_TO_GROUP.getCode()),
    // 企微家校消息
    WECHAT_SCHOOL_TEXT(getMessageTypeEnumCode(ChannelTypeEnum.WECHAT, ChannelProviderTypeEnum.DEFAULT, newIndex()), "text", "企微家校-文本消息", ChannelTypeEnum.WECHAT.getCode(), ChannelProviderTypeEnum.DEFAULT.getCode(), PushSubjectEnum.WECHAT_SCHOOL.getCode()),
    WECHAT_SCHOOL_IMAGE(getMessageTypeEnumCode(ChannelTypeEnum.WECHAT, ChannelProviderTypeEnum.DEFAULT, newIndex()), "image", "企微家校-图片消息", ChannelTypeEnum.WECHAT.getCode(), ChannelProviderTypeEnum.DEFAULT.getCode(), PushSubjectEnum.WECHAT_SCHOOL.getCode()),
    WECHAT_SCHOOL_VOICE(getMessageTypeEnumCode(ChannelTypeEnum.WECHAT, ChannelProviderTypeEnum.DEFAULT, newIndex()), "voice", "企微家校-语音消息", ChannelTypeEnum.WECHAT.getCode(), ChannelProviderTypeEnum.DEFAULT.getCode(), PushSubjectEnum.WECHAT_SCHOOL.getCode()),
    WECHAT_SCHOOL_VIDEO(getMessageTypeEnumCode(ChannelTypeEnum.WECHAT, ChannelProviderTypeEnum.DEFAULT, newIndex()), "video", "企微家校-视频消息", ChannelTypeEnum.WECHAT.getCode(), ChannelProviderTypeEnum.DEFAULT.getCode(), PushSubjectEnum.WECHAT_SCHOOL.getCode()),
    WECHAT_SCHOOL_FILE(getMessageTypeEnumCode(ChannelTypeEnum.WECHAT, ChannelProviderTypeEnum.DEFAULT, newIndex()), "file", "企微家校-文件消息", ChannelTypeEnum.WECHAT.getCode(), ChannelProviderTypeEnum.DEFAULT.getCode(), PushSubjectEnum.WECHAT_SCHOOL.getCode()),
    WECHAT_SCHOOL_NEWS(getMessageTypeEnumCode(ChannelTypeEnum.WECHAT, ChannelProviderTypeEnum.DEFAULT, newIndex()), "news", "企微家校-图文消息", ChannelTypeEnum.WECHAT.getCode(), ChannelProviderTypeEnum.DEFAULT.getCode(), PushSubjectEnum.WECHAT_SCHOOL.getCode()),
    WECHAT_SCHOOL_MPNEWS(getMessageTypeEnumCode(ChannelTypeEnum.WECHAT, ChannelProviderTypeEnum.DEFAULT, newIndex()), "mpnews", "企微家校-图文消息(mpnews)", ChannelTypeEnum.WECHAT.getCode(), ChannelProviderTypeEnum.DEFAULT.getCode(), PushSubjectEnum.WECHAT_SCHOOL.getCode()),
    WECHAT_SCHOOL_MINIPROGRAM_NOTICE(getMessageTypeEnumCode(ChannelTypeEnum.WECHAT, ChannelProviderTypeEnum.DEFAULT, newIndex()), "miniprogram", "企微家校-小程序消息", ChannelTypeEnum.WECHAT.getCode(), ChannelProviderTypeEnum.DEFAULT.getCode(), PushSubjectEnum.WECHAT_SCHOOL.getCode()),
    // 企微群机器人消息
    WECHAT_GROUP_ROBOT_TEXT(getMessageTypeEnumCode(ChannelTypeEnum.WECHAT, ChannelProviderTypeEnum.DEFAULT, newIndex()), "text", "企微群机器人-文本消息", ChannelTypeEnum.WECHAT.getCode(), ChannelProviderTypeEnum.DEFAULT.getCode(), PushSubjectEnum.WECHAT_GROUP_ROBOT.getCode()),
    WECHAT_GROUP_ROBOT_MARKDOWN(getMessageTypeEnumCode(ChannelTypeEnum.WECHAT, ChannelProviderTypeEnum.DEFAULT, newIndex()), "markdown", "企微群机器人-markdown消息", ChannelTypeEnum.WECHAT.getCode(), ChannelProviderTypeEnum.DEFAULT.getCode(), PushSubjectEnum.WECHAT_GROUP_ROBOT.getCode()),
    WECHAT_GROUP_ROBOT_IMAGE(getMessageTypeEnumCode(ChannelTypeEnum.WECHAT, ChannelProviderTypeEnum.DEFAULT, newIndex()), "image", "企微群机器人-图片消息", ChannelTypeEnum.WECHAT.getCode(), ChannelProviderTypeEnum.DEFAULT.getCode(), PushSubjectEnum.WECHAT_GROUP_ROBOT.getCode()),
    WECHAT_GROUP_ROBOT_MPNEWS(getMessageTypeEnumCode(ChannelTypeEnum.WECHAT, ChannelProviderTypeEnum.DEFAULT, newIndex()), "news", "企微群机器人-图文消息", ChannelTypeEnum.WECHAT.getCode(), ChannelProviderTypeEnum.DEFAULT.getCode(), PushSubjectEnum.WECHAT_GROUP_ROBOT.getCode()),
    WECHAT_GROUP_ROBOT_FILE(getMessageTypeEnumCode(ChannelTypeEnum.WECHAT, ChannelProviderTypeEnum.DEFAULT, newIndex()), "file", "企微群机器人-文件消息", ChannelTypeEnum.WECHAT.getCode(), ChannelProviderTypeEnum.DEFAULT.getCode(), PushSubjectEnum.WECHAT_GROUP_ROBOT.getCode()),
    WECHAT_GROUP_ROBOT_VOICE(getMessageTypeEnumCode(ChannelTypeEnum.WECHAT, ChannelProviderTypeEnum.DEFAULT, newIndex()), "voice", "企微群机器人-语音消息", ChannelTypeEnum.WECHAT.getCode(), ChannelProviderTypeEnum.DEFAULT.getCode(), PushSubjectEnum.WECHAT_GROUP_ROBOT.getCode()),
    WECHAT_GROUP_ROBOT_TEMPLATE_CARD(getMessageTypeEnumCode(ChannelTypeEnum.WECHAT, ChannelProviderTypeEnum.DEFAULT, newIndex()), "template_card", "企微群机器人-模板卡片消息", ChannelTypeEnum.WECHAT.getCode(), ChannelProviderTypeEnum.DEFAULT.getCode(), PushSubjectEnum.WECHAT_GROUP_ROBOT.getCode()),
    /******************** 飞书消息 ********************/
    FEI_SHU_TEXT(getMessageTypeEnumCode(ChannelTypeEnum.FEI_SHU, ChannelProviderTypeEnum.DEFAULT, newIndex()), "text", "飞书-文本text消息", ChannelTypeEnum.FEI_SHU.getCode(), ChannelProviderTypeEnum.DEFAULT.getCode(), PushSubjectEnum.FEI_SHU.getCode()),
    FEI_SHU_POST(getMessageTypeEnumCode(ChannelTypeEnum.FEI_SHU, ChannelProviderTypeEnum.DEFAULT, newIndex()), "post", "飞书-富文本post消息", ChannelTypeEnum.FEI_SHU.getCode(), ChannelProviderTypeEnum.DEFAULT.getCode(), PushSubjectEnum.FEI_SHU.getCode()),
    FEI_SHU_IMAGE(getMessageTypeEnumCode(ChannelTypeEnum.FEI_SHU, ChannelProviderTypeEnum.DEFAULT, newIndex()), "image", "飞书-图片image消息", ChannelTypeEnum.FEI_SHU.getCode(), ChannelProviderTypeEnum.DEFAULT.getCode(), PushSubjectEnum.FEI_SHU.getCode()),
    FEI_SHU_INTERACTIVE(getMessageTypeEnumCode(ChannelTypeEnum.FEI_SHU, ChannelProviderTypeEnum.DEFAULT, newIndex()), "interactive", "飞书-卡片interactive消息", ChannelTypeEnum.FEI_SHU.getCode(), ChannelProviderTypeEnum.DEFAULT.getCode(), PushSubjectEnum.FEI_SHU.getCode()),
    FEI_SHU_SHARE_CHAT(getMessageTypeEnumCode(ChannelTypeEnum.FEI_SHU, ChannelProviderTypeEnum.DEFAULT, newIndex()), "share_chat", "飞书-分享群名片share_chat消息", ChannelTypeEnum.FEI_SHU.getCode(), ChannelProviderTypeEnum.DEFAULT.getCode(), PushSubjectEnum.FEI_SHU.getCode()),
    FEI_SHU_SHARE_USER(getMessageTypeEnumCode(ChannelTypeEnum.FEI_SHU, ChannelProviderTypeEnum.DEFAULT, newIndex()), "share_user", "飞书-分享个人名片share_user消息", ChannelTypeEnum.FEI_SHU.getCode(), ChannelProviderTypeEnum.DEFAULT.getCode(), PushSubjectEnum.FEI_SHU.getCode()),
    FEI_SHU_AUDIO(getMessageTypeEnumCode(ChannelTypeEnum.FEI_SHU, ChannelProviderTypeEnum.DEFAULT, newIndex()), "audio", "飞书-语音audio消息", ChannelTypeEnum.FEI_SHU.getCode(), ChannelProviderTypeEnum.DEFAULT.getCode(), PushSubjectEnum.FEI_SHU.getCode()),
    FEI_SHU_MEDIA(getMessageTypeEnumCode(ChannelTypeEnum.FEI_SHU, ChannelProviderTypeEnum.DEFAULT, newIndex()), "media", "飞书-视频media消息", ChannelTypeEnum.FEI_SHU.getCode(), ChannelProviderTypeEnum.DEFAULT.getCode(), PushSubjectEnum.FEI_SHU.getCode()),
    FEI_SHU_FILE(getMessageTypeEnumCode(ChannelTypeEnum.FEI_SHU, ChannelProviderTypeEnum.DEFAULT, newIndex()), "file", "飞书-文件file消息", ChannelTypeEnum.FEI_SHU.getCode(), ChannelProviderTypeEnum.DEFAULT.getCode(), PushSubjectEnum.FEI_SHU.getCode()),
    FEI_SHU_STICKER(getMessageTypeEnumCode(ChannelTypeEnum.FEI_SHU, ChannelProviderTypeEnum.DEFAULT, newIndex()), "sticker", "飞书-表情包sticker消息", ChannelTypeEnum.FEI_SHU.getCode(), ChannelProviderTypeEnum.DEFAULT.getCode(), PushSubjectEnum.FEI_SHU.getCode()),
    /******************** 微信公众号消息 ********************/
    OFFICIAL_ACCOUNT_TEMPLATE(getMessageTypeEnumCode(ChannelTypeEnum.OFFICIAL_ACCOUNT, ChannelProviderTypeEnum.DEFAULT, newIndex()), "template", "微信公众号-模板消息", ChannelTypeEnum.OFFICIAL_ACCOUNT.getCode(), ChannelProviderTypeEnum.DEFAULT.getCode(), PushSubjectEnum.OFFICIAL_ACCOUNT_TEMPLATE.getCode())
    ;

    private static int index = 0;

    private static String newIndex() {
        return String.valueOf(++index);
    }

    private static String getMessageTypeEnumCode(ChannelTypeEnum channelTypeEnum, ChannelProviderTypeEnum channelProviderTypeEnum, String newIndex) {
        return channelTypeEnum.getCode() + CommonConstant.CODE_SEPARATE + channelProviderTypeEnum.getCode() + CommonConstant.CODE_SEPARATE + newIndex;
    }

    private final String code;
    private final String msgType;
    private final String name;
    private final String channelType;
    private final String channelProviderType;
    private final String pushSubject;

    public static MessageTypeEnum getInstanceByCode(String code) {
        for (MessageTypeEnum typeEnum : values()) {
            if (StrUtil.equals(typeEnum.getCode(), code)) {
                return typeEnum;
            }
        }
        return null;
    }
}
