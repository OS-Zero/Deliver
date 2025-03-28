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

package com.oszero.deliver.business.admin.constant.messageparam;

/**
 * @author oszero
 * @version 1.0.0
 */
public interface SmsMessageParam {
    String ALI_PARAM = """
            {
               "region": "服务地址 cn-zhangjiakou 或者 cn-beijing 或者 cn-huhehaote 等 必须",
                 "signName": "短信签名名称  必须",
                 "templateCode": "短信模板Code 必须",
                 "templateParam": "{有参数情况下必须}",
                 "outId": "外部流水扩展字段   非必须",
                 "smsUpExtendCode": "上行短信扩展码 非必须"
             }
            """;
    String TENCENT_PARAM = """
            {
               "region": "ap-beijing 或 ap-guangzhou 或 ap-nanjing 必填",
                 "smsSdkAppId": "必填",
                 "signName": "必填",
                 "templateId": "必填",
                 "templateParam": [
                   "有动态参数，必填"
                 ],
                 "extendCode": "短信码号扩展号 非必填",
                 "sessionContext": "用户的 session 内容，可以携带用户侧 ID 等上下文信息，server 会原样返回 非必填",
                 "senderId": "国内短信无需填写该项；国际/港澳台短信已申请独立 SenderId 需要填写该字段，默认使用公共 SenderId，无需填写该字段。 非必填"
             }
            """;
}
