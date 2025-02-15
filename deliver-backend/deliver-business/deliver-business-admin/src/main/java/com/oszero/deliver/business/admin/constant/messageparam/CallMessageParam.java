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
public interface CallMessageParam {
    String ALI_PARAM = """
            {
              "region": "服务地址",
              "calledShowNumber": "被叫显号  非必须",
              "ttsCode": "已通过审核的语音通知文本转语音模板或语音验证码模板的模板 ID 必须",
              "ttsParam": "模板中的变量参数   非必须",
              "playTimes": "一通电话内语音通知内容的播放次数 非必须",
              "volume": "语音通知的播放音量  非必须",
              "speed": "语速控制 非必须",
              "outId": "发起请求时预留给调用方的自定义 ID，最终会通过在回执消息中将此 ID 带回给调用方。非必须"
            }
            """;
    String TENCENT_PARAM = """
            {
               "callProvider": "tencent --指定提供商 必填",
               "region": "地域列表 ap-beijing 或者 ap-guangzhou 必填",
               "templateId": "模板 ID，在控制台审核通过的模板 ID。 必填",
               "templateParamSet": [
                   "1 模板参数，若模板没有参数，请提供为空数组。 非必填"
               ],
               "playTimes": 1,
               "sessionContext": "1 用户的 session 内容，腾讯 server 回包中会原样返回。 非必填",
               "voiceSdkAppid": "1 在语音控制台添加应用后生成的实际SdkAppid 非必填"
            }
            """;
}
