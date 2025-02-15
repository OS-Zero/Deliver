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

package com.oszero.deliver.platformclient.common;

/**
 * @author oszero
 * @version 1.0.0
 */
public interface ClientConstant {
    /********** 渠道供应商类型 **********/
    String DEFAULT = "默认平台";
    String ALI_YUN = "阿里云";
    String TENCENT = "腾讯云";
    /********** 渠道通用相关常量 **********/
    String SPILT = ",";
    String USER_ID_TYPE = "userIdType";
    String TEMPLATE_ID = "templateId";
    /********** 电话相关常量 **********/
    /********** 短信相关常量 **********/
    /********** 邮件相关常量 **********/
    /********** 钉钉相关常量 **********/
    // 钉钉工作通知相关常量
    String DING_AGENT_ID = "agent_id";
    String DING_MSG_TYPE = "msgtype";
    String DING_USER_ID_LIST = "userid_list";
    String DING_DEPT_ID_LIST = "dept_id_list";
    String DING_TO_ALL = "to_all_user";
    // 钉钉机器人相关常量
    String DING_MSG_PARAM = "msgParam";
    String DING_ROBOT_CODE = "robotCode";
    String DING_MSG_KEY = "msgKey";
    String DING_USER_IDS = "userIds";
    String DING_OPEN_CONVERSATION_ID = "openConversationId";
    /********** 企微相关常量 **********/
    String WECHAT_AGENT_ID = "agentid";
    String WECHAT_MSG_TYPE = "msgtype";
    String WECHAT_SPILT = "|";
    // 企微应用相关常量
    String WECHAT_APP_TO_USER = "touser";
    String WECHAT_APP_TO_PARTY = "toparty";
    String WECHAT_APP_TO_TAG = "totag";
    // 企微应用发布群聊相关常量
    String WECHAT_APP_TO_GROUP_CHAT_ID = "chatid";
    // 企微家校相关常量
    String WECHAT_SCHOOL_TO_PARENT_USER_ID = "to_parent_userid";
    String WECHAT_SCHOOL_TO_STUDENT_USER_ID = "to_student_userid";
    String WECHAT_SCHOOL_TO_PARTY = "to_party";
    String WECHAT_SCHOOL_TO_ALL = "toall";
    // 企微机器人相关常量
    String WECHAT_ROBOT_WEBHOOK = "webhook";
    /********** 飞书相关常量 **********/
    String FEI_SHU_USER_ID = "user_id";
    String FEI_SHU_EMAIL = "email";
    String FEI_SHU_CHAT_ID = "chat_id";
    String FEI_SHU_DEPT_ID = "department_id";
    String FEI_SHU_USER_IDS = "user_ids";
    String FEI_SHU_DEPT_IDS = "department_ids";
    String FEI_SHU_MSG_TYPE = "msg_type";
    /********** 微信公众号相关常量 **********/
    String OFFICIAL_ACCOUNT_USER_ID = "touser";
}
