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

package com.oszero.deliver.server.pretreatment.constant;

/**
 * 消息前置处理相关常量
 *
 * @author oszero
 * @version 1.0.0
 */
public interface PretreatmentCodeConstant {

    /**
     * 企业账号的一些发送常量
     */
    String COMPANY_ACCOUNT_CALL = "1-1";
    String COMPANY_ACCOUNT_SMS = "1-2";
    String COMPANY_ACCOUNT_MAIL = "1-3";
    String COMPANY_ACCOUNT_DING = "1-4";
    String COMPANY_ACCOUNT_WECHAT = "1-5";
    String COMPANY_ACCOUNT_FEI_SHU = "1-6";

    /**
     * 电话号码的一些发送常量
     */
    String PHONE_CALL = "2-1";
    String PHONE_SMS = "2-2";
    String PHONE_DING = "2-4";
    String PHONE_WECHAT = "2-5";
    String PHONE_FEI_SHU = "2-6";

    /**
     * 邮箱的一些发送常量
     */
    String MAIL_MAIL = "3-3";

    /**
     * 平台 ID 的一些发送常量
     */
    String USERID_DING = "4-4";
    String USERID_WECHAT = "4-5";
    String USERID_FEI_SHU = "4-6";

}
