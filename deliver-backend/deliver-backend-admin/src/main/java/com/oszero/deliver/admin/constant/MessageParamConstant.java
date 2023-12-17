package com.oszero.deliver.admin.constant;

import com.oszero.deliver.admin.enums.ChannelTypeEnum;
import com.oszero.deliver.admin.enums.MessageTypeEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * 消息参数常量
 *
 * @author oszero
 * @version 1.0.0
 */
public class MessageParamConstant {
    public static final Map<String, String> MESSAGE_PARAM_MAP = new HashMap<>();
    public static final String H = "-";

    static {
        // 电话消息参数

        // 短信消息参数

        // 邮件消息参数
        MESSAGE_PARAM_MAP.put(ChannelTypeEnum.MAIL.getCode() + H + MessageTypeEnum.TEXT.getCode(), """
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
        MESSAGE_PARAM_MAP.put(ChannelTypeEnum.DING.getCode() + H + MessageTypeEnum.TEXT.getCode(), """
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
        MESSAGE_PARAM_MAP.put(ChannelTypeEnum.DING.getCode() + H + MessageTypeEnum.DING_IMAGE.getCode(), """
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
        MESSAGE_PARAM_MAP.put(ChannelTypeEnum.DING.getCode() + H + MessageTypeEnum.DING_VOICE.getCode(), """
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
        MESSAGE_PARAM_MAP.put(ChannelTypeEnum.DING.getCode() + H + MessageTypeEnum.DING_FILE.getCode(), """
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
        MESSAGE_PARAM_MAP.put(ChannelTypeEnum.DING.getCode() + H + MessageTypeEnum.DING_LINK.getCode(), """
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
        MESSAGE_PARAM_MAP.put(ChannelTypeEnum.DING.getCode() + H + MessageTypeEnum.DING_OA.getCode(), """
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
        MESSAGE_PARAM_MAP.put(ChannelTypeEnum.DING.getCode() + H + MessageTypeEnum.DING_MARKDOWN.getCode(), """
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
        MESSAGE_PARAM_MAP.put(ChannelTypeEnum.DING.getCode() + H + MessageTypeEnum.DING_CARD.getCode(), """
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

        // 飞书消息参数
        MESSAGE_PARAM_MAP.put(ChannelTypeEnum.FEI_SHU.getCode() + H + MessageTypeEnum.TEXT.getCode(), """
                {
                  "feiShuUserIdType": "user_id 或者 email 或者 chat_id 或者 department_id",
                  "content": {
                    "text": "test content"
                  }
                }
                """);
        MESSAGE_PARAM_MAP.put(ChannelTypeEnum.FEI_SHU.getCode() + H + MessageTypeEnum.FEI_SHU_POST.getCode(), """
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
        MESSAGE_PARAM_MAP.put(ChannelTypeEnum.FEI_SHU.getCode() + H + MessageTypeEnum.FEI_SHU_IMAGE.getCode(), """
                {
                  "feiShuUserIdType": "user_id 或者 email 或者 chat_id 或者 department_id",
                  "content": {
                    "image_key": ""
                  }
                }
                """);
        MESSAGE_PARAM_MAP.put(ChannelTypeEnum.FEI_SHU.getCode() + H + MessageTypeEnum.FEI_SHU_INTERACTIVE.getCode(), """
                {
                  "feiShuUserIdType": "user_id 或者 email 或者 chat_id 或者 department_id",
                  "card": {}
                }
                """);
        MESSAGE_PARAM_MAP.put(ChannelTypeEnum.FEI_SHU.getCode() + H + MessageTypeEnum.FEI_SHU_SHARE_CHAT.getCode(), """
                {
                  "feiShuUserIdType": "user_id 或者 email 或者 chat_id 或者 department_id",
                  "content": {
                    "chat_id": ""
                  }
                }
                """);
        MESSAGE_PARAM_MAP.put(ChannelTypeEnum.FEI_SHU.getCode() + H + MessageTypeEnum.FEI_SHU_SHARE_USER.getCode(), """
                {
                  "feiShuUserIdType": "user_id 或者 email 或者 chat_id",
                  "content": {
                    "user_id": ""
                  }
                }
                """);
        MESSAGE_PARAM_MAP.put(ChannelTypeEnum.FEI_SHU.getCode() + H + MessageTypeEnum.FEI_SHU_AUDIO.getCode(), """
                {
                  "feiShuUserIdType": "user_id 或者 email 或者 chat_id",
                  "content": {
                    "file_key": ""
                  }
                }
                """);
        MESSAGE_PARAM_MAP.put(ChannelTypeEnum.FEI_SHU.getCode() + H + MessageTypeEnum.FEI_SHU_MEDIA.getCode(), """
                {
                  "feiShuUserIdType": "user_id 或者 email 或者 chat_id",
                  "content": {
                    "file_key": "75235e0c-430a-a99b-8446610223cg",
                    "image_key": "img_xxxxxx"
                  }
                }
                """);
        MESSAGE_PARAM_MAP.put(ChannelTypeEnum.FEI_SHU.getCode() + H + MessageTypeEnum.FEI_SHU_FILE.getCode(), """
                {
                  "feiShuUserIdType": "user_id 或者 email 或者 chat_id",
                  "content": {
                    "file_key": "75235e0c-430a-a99b-8446610223cg"
                  }
                }
                """);
        MESSAGE_PARAM_MAP.put(ChannelTypeEnum.FEI_SHU.getCode() + H + MessageTypeEnum.FEI_SHU_STICKER.getCode(), """
                {
                  "feiShuUserIdType": "user_id 或者 email 或者 chat_id",
                  "content": {
                    "file_key": "75235e0c-430a-a99b-8446610223cg"
                  }
                }
                """);
    }
}
