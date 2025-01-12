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
    /********** 电话相关常量 **********/
    /********** 短信相关常量 **********/
    /********** 邮件相关常量 **********/
    /********** 钉钉相关常量 **********/
    String DING_AGENT_ID = "agent_id";
    String DING_MSG_TYPE = "msgtype";
    String DING_MSG_PARAM = "msgParam";
    String DING_ROBOT_CODE = "robotCode";
    String DING_MSG_KEY = "msgKey";
    String DING_OPEN_CONVERSATION_ID = "openConversationId";
    /********** 企微相关常量 **********/
    String WECHAT_AGENT_ID = "agentid";
    String WECHAT_MSG_TYPE = "msgtype";
    String WECHAT_SPILT = "|";
    /********** 飞书相关常量 **********/
}
