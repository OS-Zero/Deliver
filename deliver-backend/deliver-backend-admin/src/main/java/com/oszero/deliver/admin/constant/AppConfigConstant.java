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

package com.oszero.deliver.admin.constant;

/**
 * APP 配置常量
 *
 * @author oszero
 * @version 1.0.0
 */
public interface AppConfigConstant {

    String CALL_CONFIG = "{}";

    String SMS_CONFIG = "{}";

    String MAIL_CONFIG = """
            {
              "host": "xxx",
              "username": "xxx",
              "password": "xxx",
              "auth": "true",
              "sslEnable": "true"
            }
            """;

    String DING_CONFIG = """
            {
              "agentId": 995,
              "appKey": "xxx",
              "appSecret": "xxx",
              "robotCode": "xxx"
            }
            """;

    String WECHAT_CONFIG = """
            {
              "corpid": "xxx",
              "corpsecret": "xxx",
              "agentid": "xxx"
            }
            """;

    String FEI_SHU_CONFIG = """
            {
              "appId": "xxx",
              "appSecret": "xxx"
            }
            """;

}
