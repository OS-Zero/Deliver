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

/**
 * @author oszero
 * @version 1.0.0
 */
public interface AppConfigConstant {

    /**********空配置**********/
    String NO_CONFIG = """
            {}
            """;

    /**********电话配置**********/
    String ALI_YUN_CALL_CONFIG = """
            {
                "accessKeyId": "",
                "accessKeySecret": ""
            }
            """;
    String TENCENT_CALL_CONFIG = """
            {
                "secretId": "",
                "secretKey": ""
            }
            """;
    /**********短信配置**********/
    String ALI_YUN_SMS_CONFIG = """
            {
                "accessKeyId": "",
                "accessKeySecret": ""
            }
            """;
    String TENCENT_SMS_CONFIG = """
            {
                "secretId": "",
                "secretKey": ""
            }
            """;
    /**********邮箱配置**********/
    String MAIL_CONFIG = """
            {
              "host": "",
              "username": "",
              "password": "",
              "auth": "true",
              "sslEnable": "true"
            }
            """;
    /**********钉钉配置**********/
    String DING_CONFIG = """
            {
              "agentId": 995111,
              "appKey": "",
              "appSecret": "",
              "robotCode": ""
            }
            """;
    /**********企业微信配置**********/
    String WECHAT_CONFIG = """
            {
              "corpid": "",
              "corpsecret": "",
              "agentid": ""
            }
            """;
    /**********飞书配置**********/
    String FEI_SHU_CONFIG = """
            {
              "appId": "",
              "appSecret": ""
            }
            """;

    /**********微信公众号配置**********/
    String OFFICIAL_ACCOUNT_CONFIG = """
            {
              "appid": "",
              "secret": ""
            }
            """;

}
