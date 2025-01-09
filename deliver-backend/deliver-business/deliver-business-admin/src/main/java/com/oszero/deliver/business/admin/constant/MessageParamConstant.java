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

import com.oszero.deliver.business.common.enums.ChannelTypeEnum;
import com.oszero.deliver.business.common.enums.MessageTypeEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * @author oszero
 * @version 1.0.0
 */
public class MessageParamConstant {
    private static final Map<String, String> MESSAGE_PARAM_MAP = new HashMap<>();

    static {
        // 电话消息参数
        MESSAGE_PARAM_MAP.put(getKeyByCode(ChannelTypeEnum.CALL, MessageTypeEnum.COMMON_TEXT), """
                {
                   "aliyun": {
                     "callProvider": "aliyun --指定提供商 必须",
                     "region": "服务地址",
                     "calledShowNumber": "被叫显号  非必须",
                     "ttsCode": "已通过审核的语音通知文本转语音模板或语音验证码模板的模板 ID 必须",
                     "ttsParam": "模板中的变量参数   非必须",
                     "playTimes": "一通电话内语音通知内容的播放次数 非必须",
                     "volume": "语音通知的播放音量  非必须",
                     "speed": "语速控制 非必须",
                     "outId": "发起请求时预留给调用方的自定义 ID，最终会通过在回执消息中将此 ID 带回给调用方。非必须"
                   },
                   "tencent": {
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
                 }
                """);
        // 短信消息参数
        MESSAGE_PARAM_MAP.put(getKeyByCode(ChannelTypeEnum.SMS, MessageTypeEnum.COMMON_TEXT), """
                {
                   "aliyun": {
                     "smsProvider": "aliyun --指定提供商 必须",
                     "region": "服务地址 cn-zhangjiakou 或者 cn-beijing 或者 cn-huhehaote 等 必须",
                     "signName": "短信签名名称  必须",
                     "templateCode": "短信模板Code 必须",
                     "templateParam": "{有参数情况下必须}",
                     "outId": "外部流水扩展字段   非必须",
                     "smsUpExtendCode": "上行短信扩展码 非必须"
                   },
                   "tencent": {
                     "smsProvider": "tencent 必填",
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
                 }
                """);
        // 邮件消息参数
        MESSAGE_PARAM_MAP.put(getKeyByCode(ChannelTypeEnum.MAIL, MessageTypeEnum.COMMON_TEXT), """
                {
                    "title": "dsadas",
                    "content": "test",
                    "toCC": [
                        "xxx"
                    ],
                    "toBCC": [
                        "xxx"
                    ],
                    "htmlFlag": true
                }
                """);

        // 钉钉消息参数
        MESSAGE_PARAM_MAP.put(getKeyByCode(ChannelTypeEnum.DING, MessageTypeEnum.COMMON_TEXT), """
                {
                  "pushSubject": "workNotice 或者 robot",
                  "dingUserIdType": "userid_list 或者 dept_id_list 或者 userIds 或者 openConversationId",
                  "msgParam": {
                    "content": "xxxx"
                  },
                  "msg": {
                    "text": {
                      "content": "月会通知"
                    }
                  }
                }
                """);
        MESSAGE_PARAM_MAP.put(getKeyByCode(ChannelTypeEnum.DING, MessageTypeEnum.DING_IMAGE), """
                {
                  "dingUserIdType": "userid_list 或者 dept_id_list 或者 userIds 或者 openConversationId",
                  "pushSubject": "workNotice 或者 robot",
                  "msgParam": {
                    "photoURL": "xxxx"
                  },
                  "msg": {
                    "image": {
                      "media_id": ""
                    }
                  }
                }
                """);
        MESSAGE_PARAM_MAP.put(getKeyByCode(ChannelTypeEnum.DING, MessageTypeEnum.DING_VOICE), """
                {
                  "dingUserIdType": "userid_list 或者 dept_id_list 或者 userIds 或者 openConversationId",
                  "pushSubject": "workNotice 或者 robot",
                  "msgParam": {
                    "mediaId": "@IR_P********nFkfhsisbf4A",
                    "duration": "xxxxx"
                  },
                  "msg": {
                    "voice": {
                      "media_id": "",
                      "duration": "10"
                    }
                  }
                }
                """);
        MESSAGE_PARAM_MAP.put(getKeyByCode(ChannelTypeEnum.DING, MessageTypeEnum.DING_FILE), """
                {
                  "dingUserIdType": "userid_list 或者 dept_id_list 或者 userIds 或者 openConversationId",
                  "pushSubject": "workNotice 或者 robot",
                  "msgParam": {
                    "mediaId": "@lAz*********shRs5m2pRL",
                    "fileName": "表格.xlsx",
                    "fileType": "xlsx"
                  },
                  "msg": {
                    "file": {
                      "media_id": "MEDIA_ID"
                    }
                  }
                }
                """);
        MESSAGE_PARAM_MAP.put(getKeyByCode(ChannelTypeEnum.DING, MessageTypeEnum.DING_LINK), """
                {
                  "dingUserIdType": "userid_list 或者 dept_id_list 或者 userIds 或者 openConversationId",
                  "pushSubject": "workNotice 或者 robot",
                  "msgParam": {
                    "text": "消息内容测试",
                    "title": "sampleLink消息测试",
                    "picUrl": "@lADOADmaWMzazQKA",
                    "messageUrl": "http://dingtalk.com"
                  },
                  "msg": {
                    "link": {
                      "messageUrl": "http://s.dingtalk.com/market/dingtalk/error_code.php",
                      "picUrl": "@lALOACZwe2Rk",
                      "title": "测试",
                      "text": "测试"
                    }
                  }
                }
                """);
        MESSAGE_PARAM_MAP.put(getKeyByCode(ChannelTypeEnum.DING, MessageTypeEnum.DING_OA), """
                {
                  "dingUserIdType": "userid_list 或者 dept_id_list",
                  "pushSubject": "workNotice",
                  "msg": {
                    "oa": {
                      "message_url": "http://dingtalk.com",
                      "head": {
                        "bgcolor": "FFBBBBBB",
                        "text": "头部标题"
                      },
                      "body": {
                        "title": "正文标题",
                        "form": [
                          {
                            "key": "姓名:",
                            "value": "黑子你家哥哥"
                          },
                          {
                            "key": "年龄:",
                            "value": "20"
                          },
                          {
                            "key": "身高:",
                            "value": "1.8米"
                          },
                          {
                            "key": "体重:",
                            "value": "130斤"
                          },
                          {
                            "key": "学历:",
                            "value": "本科"
                          },
                          {
                            "key": "爱好:",
                            "value": "唱跳RAP篮球"
                          }
                        ],
                        "rich": {
                          "num": "15.6",
                          "unit": "元"
                        },
                        "content": "大段文本大段文本大段文本大段文本大段文本大段文本",
                        "image": "@lADOADmaWMzazQKA",
                        "file_count": "3",
                        "author": "李四 "
                      }
                    }
                  }
                }
                """);
        MESSAGE_PARAM_MAP.put(getKeyByCode(ChannelTypeEnum.DING, MessageTypeEnum.DING_MARKDOWN), """
                {
                  "dingUserIdType": "userid_list 或者 dept_id_list 或者 userIds 或者 openConversationId",
                  "pushSubject": "workNotice 或者 robot",
                  "msgParam": {
                    "title": "xxxx",
                    "text": "xxxx"
                  },
                  "msg": {
                    "markdown": {
                      "title": "首屏会话透出的展示内容",
                      "text": "# 这是支持markdown的文本   \\n   ## 标题2    \\n   * 列表1   \\n  ![alt 啊](https://img.alicdn.com/tps/TB1XLjqNVXXXXc4XVXXXXXXXXXX-170-64.png)"
                    }
                  }
                }
                """);
        MESSAGE_PARAM_MAP.put(getKeyByCode(ChannelTypeEnum.DING, MessageTypeEnum.DING_CARD), """
                {
                  "dingUserIdType": "userid_list 或者 dept_id_list 或者 userIds 或者 openConversationId",
                  "pushSubject": "workNotice 或者 robot",
                  "msgParam": {
                    "title": "测试标题",
                    "text": "内容测试",
                    "singleTitle": "查看详情",
                    "singleURL": "https://open.dingtalk.com"
                  },
                  "msg": {
                    "action_card": {
                      "title": "是透出到会话列表和通知的文案",
                      "markdown": "支持markdown格式的正文内容",
                      "single_title": "查看详情",
                      "single_url": "https://open.dingtalk.com"
                    }
                  }
                }
                """);

        // 企业微信消息参数
        MESSAGE_PARAM_MAP.put(getKeyByCode(ChannelTypeEnum.DING, MessageTypeEnum.COMMON_TEXT), """
                {
                  "pushSubject": "app 或者 robot",
                  "wechatUserIdType": "touser 或者 toparty 或者 totag 或者 to_parent_userid 或者 to_student_userid 或者 to_party 或者 toall 或者 chatid 或者 webhook",
                  "text": {
                    "content": "你的快递已到，请携带工卡前往邮件中心领取。\\n出发前可查看<a href=\\"http://work.weixin.qq.com\\">邮件中心视频实况</a>，聪明避开排队。"
                  },
                  "safe": 0,
                  "enable_id_trans": 0,
                  "enable_duplicate_check": 0,
                  "duplicate_check_interval": 1800
                }
                """);
        //TODO:更多参数待补充

        // 飞书消息参数
        MESSAGE_PARAM_MAP.put(getKeyByCode(ChannelTypeEnum.FEI_SHU, MessageTypeEnum.COMMON_TEXT), """
                {
                  "feiShuUserIdType": "user_id 或者 email 或者 chat_id 或者 department_id",
                  "content": {
                    "text": "test content"
                  }
                }
                """);
        MESSAGE_PARAM_MAP.put(getKeyByCode(ChannelTypeEnum.FEI_SHU, MessageTypeEnum.FEI_SHU_POST), """
                {
                  "feiShuUserIdType": "user_id 或者 email 或者 chat_id 或者 department_id",
                  "content": {
                    "post": {
                      "zh_cn": {
                        "title": "我是一个标题",
                        "content": [
                          [
                            {
                              "tag": "text",
                              "text": "第一行"
                            },
                            {
                              "tag": "a",
                              "href": "http://www.feishu.cn",
                              "text": "飞书"
                            }
                          ]
                        ]
                      },
                      "en_us": {
                        "title": "I am a title",
                        "content": [
                          [
                            {
                              "tag": "text",
                              "text": "first line"
                            },
                            {
                              "tag": "a",
                              "href": "http://www.feishu.cn",
                              "text": "feishu"
                            }
                          ]
                        ]
                      }
                    }
                  }
                }
                """);
        MESSAGE_PARAM_MAP.put(getKeyByCode(ChannelTypeEnum.FEI_SHU, MessageTypeEnum.FEI_SHU_IMAGE), """
                {
                  "feiShuUserIdType": "user_id 或者 email 或者 chat_id 或者 department_id",
                  "content": {
                    "image_key": ""
                  }
                }
                """);
        MESSAGE_PARAM_MAP.put(getKeyByCode(ChannelTypeEnum.FEI_SHU, MessageTypeEnum.FEI_SHU_INTERACTIVE), """
                {
                  "feiShuUserIdType": "user_id 或者 email 或者 chat_id 或者 department_id",
                  "card": {}
                }
                """);
        MESSAGE_PARAM_MAP.put(getKeyByCode(ChannelTypeEnum.FEI_SHU, MessageTypeEnum.FEI_SHU_SHARE_CHAT), """
                {
                  "feiShuUserIdType": "user_id 或者 email 或者 chat_id 或者 department_id",
                  "content": {
                    "chat_id": ""
                  }
                }
                """);
        MESSAGE_PARAM_MAP.put(getKeyByCode(ChannelTypeEnum.FEI_SHU, MessageTypeEnum.FEI_SHU_SHARE_USER), """
                {
                  "feiShuUserIdType": "user_id 或者 email 或者 chat_id",
                  "content": {
                    "user_id": ""
                  }
                }
                """);
        MESSAGE_PARAM_MAP.put(getKeyByCode(ChannelTypeEnum.FEI_SHU, MessageTypeEnum.FEI_SHU_AUDIO), """
                {
                  "feiShuUserIdType": "user_id 或者 email 或者 chat_id",
                  "content": {
                    "file_key": ""
                  }
                }
                """);
        MESSAGE_PARAM_MAP.put(getKeyByCode(ChannelTypeEnum.FEI_SHU, MessageTypeEnum.FEI_SHU_MEDIA), """
                {
                  "feiShuUserIdType": "user_id 或者 email 或者 chat_id",
                  "content": {
                    "file_key": "75235e0c-430a-a99b-8446610223cg",
                    "image_key": "img_xxxxxx"
                  }
                }
                """);
        MESSAGE_PARAM_MAP.put(getKeyByCode(ChannelTypeEnum.FEI_SHU, MessageTypeEnum.FEI_SHU_FILE), """
                {
                  "feiShuUserIdType": "user_id 或者 email 或者 chat_id",
                  "content": {
                    "file_key": "75235e0c-430a-a99b-8446610223cg"
                  }
                }
                """);
        MESSAGE_PARAM_MAP.put(getKeyByCode(ChannelTypeEnum.FEI_SHU, MessageTypeEnum.FEI_SHU_STICKER), """
                {
                  "feiShuUserIdType": "user_id 或者 email 或者 chat_id",
                  "content": {
                    "file_key": "75235e0c-430a-a99b-8446610223cg"
                  }
                }
                """);

    }

    private static String getKeyByCode(ChannelTypeEnum channelTypeEnum, MessageTypeEnum messageTypeEnum) {
        return channelTypeEnum.getCode() + com.oszero.deliver.business.common.constant.CommonConstant.CODE_SEPARATE + messageTypeEnum.getCode();
    }

    public static String getMessageParamJsonConfig(String channelTypeEnumCode, String messageTypeEnumCode) {
        return MESSAGE_PARAM_MAP.getOrDefault(channelTypeEnumCode + com.oszero.deliver.business.common.constant.CommonConstant.CODE_SEPARATE + messageTypeEnumCode, "{}");
    }
}
