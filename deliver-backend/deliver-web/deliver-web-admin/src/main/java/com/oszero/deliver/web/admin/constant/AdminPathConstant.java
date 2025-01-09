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

package com.oszero.deliver.web.admin.constant;

/**
 * @author oszero
 * @version 1.0.0
 */
public interface AdminPathConstant {
    /********** 用户模块 **********/
    String USER_PREFIX_PATH = "/user";
    String USER_LOGIN = USER_PREFIX_PATH + "/login";
    String USER_REGISTER = USER_PREFIX_PATH + "/register";
    String USER_FORGET_PASSWORD = USER_PREFIX_PATH + "/forgetPassword";
    String USER_GET_VERIFICATION_CODE = USER_PREFIX_PATH + "/getVerificationCode";
    String USER_UPDATE_PASSWORD = USER_PREFIX_PATH + "/updatePassword";
    String USER_GET_CURRENT_LOGIN_USER_INFO = USER_PREFIX_PATH + "/getCurrentLoginUserInfo";
    String USER_LOGOUT = USER_PREFIX_PATH + "/logout";
    /********** 启动模块 **********/
    String STARTUP = "/startup";
    /********** 系统参数模块 **********/
    String SYSTEM_PARAM_PREFIX_PATH = "/systemParam";
    String SYSTEM_PARAM_GET_CHANNEL_TYPE = SYSTEM_PARAM_PREFIX_PATH + "/getChannelType";
    String SYSTEM_PARAM_GET_PARAM = SYSTEM_PARAM_PREFIX_PATH + "/getParam";
    String SYSTEM_PARAM_GET_APP_CONFIG = SYSTEM_PARAM_PREFIX_PATH + "/getAppConfig";
    String SYSTEM_PARAM_GET_PLATFORM_FILE_TYPE = SYSTEM_PARAM_PREFIX_PATH + "/getPlatformFileType";
    String SYSTEM_PARAM_GET_MESSAGE_PARAM = SYSTEM_PARAM_PREFIX_PATH + "/getMessageParam";
    /********** 分组模块 **********/
    String GROUP_PREFIX_PATH = "/group";
    String GROUP_GET_GROUP_DATA = GROUP_PREFIX_PATH + "/getGroupData";
    String GROUP_SAVE = GROUP_PREFIX_PATH + "/save";
    String GROUP_UPDATE = GROUP_PREFIX_PATH + "/update";
    String GROUP_DELETE = GROUP_PREFIX_PATH + "/delete";
    String GROUP_TOP_UP = GROUP_PREFIX_PATH + "/topUp";
    /********** 消息模板模块 **********/
    String MESSAGE_TEMPLATE_PREFIX_PATH = "/messageTemplate";
    String MESSAGE_TEMPLATE_SEARCH = MESSAGE_TEMPLATE_PREFIX_PATH + "/search";
    String MESSAGE_TEMPLATE_SAVE = MESSAGE_TEMPLATE_PREFIX_PATH + "/save";
    String MESSAGE_TEMPLATE_UPDATE = MESSAGE_TEMPLATE_PREFIX_PATH + "/update";
    String MESSAGE_TEMPLATE_UPDATE_STATUS = MESSAGE_TEMPLATE_PREFIX_PATH + "/updateStatus";
    String MESSAGE_TEMPLATE_DELETE = MESSAGE_TEMPLATE_PREFIX_PATH + "/delete";
    String MESSAGE_TEMPLATE_TEST_SEND_MESSAGE = MESSAGE_TEMPLATE_PREFIX_PATH + "/testSendMessage";
    /********** 渠道应用模块 **********/
    String CHANNEL_APP_PREFIX_PATH = "/channelApp";
    String CHANNEL_APP_SEARCH = CHANNEL_APP_PREFIX_PATH + "/search";
    String CHANNEL_APP_SAVE = CHANNEL_APP_PREFIX_PATH + "/save";
    String CHANNEL_APP_UPDATE = CHANNEL_APP_PREFIX_PATH + "/update";
    String CHANNEL_APP_UPDATE_STATUS = CHANNEL_APP_PREFIX_PATH + "/updateStatus";
    String CHANNEL_APP_DELETE = CHANNEL_APP_PREFIX_PATH + "/delete";
    String CHANNEL_APP_GET_APP_BY_CHANNEL = CHANNEL_APP_PREFIX_PATH + "/getAppByChannel";
    /********** 平台文件模块 **********/
    String PLATFORM_FILE_PREFIX_PATH = "/platformFile";
    String PLATFORM_FILE_SEARCH = PLATFORM_FILE_PREFIX_PATH + "/search";
    String PLATFORM_FILE_UPLOAD_FILE = PLATFORM_FILE_PREFIX_PATH + "/uploadFile";
    /********** 群发任务模块 **********/
    String SEND_TASK_PREFIX_PATH = "/sendTask";
    String SEND_TASK_SEARCH = SEND_TASK_PREFIX_PATH + "/search";
    String SEND_TASK_SAVE = SEND_TASK_PREFIX_PATH + "/save";
    String SEND_TASK_UPDATE = SEND_TASK_PREFIX_PATH + "/update";
    String SEND_TASK_UPDATE_STATUS = SEND_TASK_PREFIX_PATH + "/updateStatus";
    String SEND_TASK_DELETE = SEND_TASK_PREFIX_PATH + "/delete";
    String SEND_TASK_SEND_REAL_TIME_MESSAGE = SEND_TASK_PREFIX_PATH + "/sendRealTimeMessage";
    /********** 人群模块 **********/
    String PEOPLE_GROUP_PREFIX_PATH = "/peopleGroup";
    String PEOPLE_GROUP_SEARCH = PEOPLE_GROUP_PREFIX_PATH + "/search";
    String PEOPLE_GROUP_SAVE = PEOPLE_GROUP_PREFIX_PATH + "/save";
    String PEOPLE_GROUP_UPDATE = PEOPLE_GROUP_PREFIX_PATH + "/update";
    String PEOPLE_GROUP_DELETE = PEOPLE_GROUP_PREFIX_PATH + "/delete";
    String PEOPLE_GROUP_GET_EXCEL_TEMPLATE_FILE = PEOPLE_GROUP_PREFIX_PATH + "/getExcelTemplateFile";
    String PEOPLE_GROUP_ANALYSIS_EXCEL_TEMPLATE_FILE = PEOPLE_GROUP_PREFIX_PATH + "/analysisExcelTemplateFile";
}
