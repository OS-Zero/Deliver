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

package com.oszero.deliver.business.server.limit;

/**
 * @author oszero
 * @version 1.0.2
 */
public interface ClientLimitConstant {
    /********** 电话 **********/
    String ALI_YUN_SEND_CALL = "aliYunSendCall";
    String TENCENT_SEND_CALL = "tencentSendCall";
    /********** 短信 **********/
    String ALI_YUN_SEND_SMS = "aliYunSendSms";
    String TENCENT_SEND_SMS = "tencentSendSms";
    /********** 邮件 **********/
    String SEND_MAIL = "sendMail";
    /********** 钉钉 **********/
    String DING_SEND_WORK_NOTICE = "dingSendWorkNotice";
    String DING_SEND_ROBOT = "dingSendRobot";
    /********** 企业微信 **********/
    String WE_CHAT_SEND_APP_MESSAGE = "weChatSendAppMessage";
    String WE_CHAT_SEND_APP_GROUP_MESSAGE = "weChatSendAppGroupMessage";
    String WE_CHAT_SEND_APP_SCHOOL_MESSAGE = "weChatSendAppSchoolMessage";
    String WE_CHAT_SEND_ROBOT_MESSAGE = "weChatSendRobotMessage";
    /********** 飞书 **********/
    String FEI_SHU_SEND_MESSAGE = "feiShuSendMessage";
    String FEI_SHU_SEND_MESSAGE_BATCH = "feiShuSendMessageBatch";
    /********** 微信公众号 **********/
    String OFFICIAL_ACCOUNT_SEND_MESSAGE = "officialAccountSendMessage";
}
