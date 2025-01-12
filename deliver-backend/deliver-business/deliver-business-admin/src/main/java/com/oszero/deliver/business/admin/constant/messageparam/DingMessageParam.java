package com.oszero.deliver.business.admin.constant.messageparam;

/**
 * @author oszero
 * @version 1.0.0
 */
public interface DingMessageParam {
    // 工作通知
    String WORK_NOTICE_TEXT_PARAM = """
            {
              "pushSubject": "workNotice",
              "userIdType": "userid_list 或者 dept_id_list 或者 to_all_user",
              "msg": {
                "text": {
                  "content": "月会通知"
                }
              }
            }
            """;
    String WORK_NOTICE_IMAGE_PARAM = """
            {
              "pushSubject": "workNotice",
              "userIdType": "userid_list 或者 dept_id_list 或者 to_all_user",
              "msg": {
                "image": {
                  "media_id": "@lADOADmaWMzazQKA"
                }
              }
            }
            """;
    String WORK_NOTICE_VOICE_PARAM = """
            {
              "pushSubject": "workNotice",
              "userIdType": "userid_list 或者 dept_id_list 或者 to_all_user",
              "msg": {
                "voice": {
                  "media_id": "@lADOADmaWMzazQKA",
                  "duration": "10"
                }
              }
            }
            """;
    String WORK_NOTICE_FILE_PARAM = """
            {
              "pushSubject": "workNotice",
              "userIdType": "userid_list 或者 dept_id_list 或者 to_all_user",
              "msg": {
                "file": {
                  "media_id": "MEDIA_ID"
                }
              }
            }
            """;
    String WORK_NOTICE_LINK_PARAM = """
            {
              "pushSubject": "workNotice",
              "userIdType": "userid_list 或者 dept_id_list 或者 to_all_user",
              "msg": {
                "link": {
                  "messageUrl": "http://s.dingtalk.com/market/dingtalk/error_code.php",
                  "picUrl":"@lALOACZwe2Rk",
                  "title": "测试",
                  "text": "测试"
                }
              }
            }
            """;
    String WORK_NOTICE_OA_PARAM = """
            {
              "pushSubject": "workNotice",
              "userIdType": "userid_list 或者 dept_id_list 或者 to_all_user",
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
                        "value": "张三"
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
                        "value": "打球、听音乐"
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
            """;
    String WORK_NOTICE_MARKDOWN_PARAM = """
            {
              "pushSubject": "workNotice",
              "userIdType": "userid_list 或者 dept_id_list 或者 to_all_user",
              "msg": {
                "markdown": {
                  "title": "首屏会话透出的展示内容",
                  "text": "# 这是支持markdown的文本   \\n   ## 标题2    \\n   * 列表1   \\n  ![alt 啊](https://img.alicdn.com/tps/TB1XLjqNVXXXXc4XVXXXXXXXXXX-170-64.png)"
                }
              }
            }
            """;
    String WORK_NOTICE_CARD_PARAM = """
            {
              "pushSubject": "workNotice",
              "userIdType": "userid_list 或者 dept_id_list 或者 to_all_user",
              "msg": {
                "action_card": {
                  "title": "是透出到会话列表和通知的文案",
                  "markdown": "支持markdown格式的正文内容",
                  "single_title": "查看详情",
                  "single_url": "https://open.dingtalk.com"
                }
              }
            }
            """;
    // 机器人消息
    String ROBOT_TEXT_PARAM = """
            {
              "pushSubject": "robot",
              "userIdType": "userIds 或者 openConversationId",
              "msgParam": {
                "content": "xxxx"
              }
            }
            """;
    String ROBOT_MARKDOWN_PARAM = """
            {
              "pushSubject": "robot",
              "userIdType": "userIds 或者 openConversationId",
              "msgParam": {
                "title": "xxxx",
                "text": "xxxx"
              }
            }
            """;
    String ROBOT_IMAGE_MSG_PARAM = """
            {
              "pushSubject": "robot",
              "userIdType": "userIds 或者 openConversationId",
              "msgParam": {
                "photoURL": "xxxx"
              }
            }
            """;
    String ROBOT_LINK_PARAM = """
            {
              "pushSubject": "robot",
              "userIdType": "userIds 或者 openConversationId",
              "msgParam": {
                "text": "消息内容测试",
                "title": "sampleLink消息测试",
                "picUrl": "@lADOADmaWMzazQKA",
                "messageUrl": "http://dingtalk.com"
              }
            }
            """;
    String ROBOT_ACTION_CARD_PARAM = """
            {
              "pushSubject": "robot",
              "userIdType": "userIds 或者 openConversationId",
              "msgParam": {
                "title": "测试标题",
                "text": "内容测试",
                "singleTitle": "查看详情",
                "singleURL": "https://open.dingtalk.com"
              }
            }
            """;
    String ROBOT_ACTION_CARD_2_PARAM = """
            {
              "pushSubject": "robot",
              "userIdType": "userIds 或者 openConversationId",
              "msgParam": {
                "title": "消息标题测试",
                "text": "消息正文测试",
                "actionTitle1": "一个按钮",
                "actionURL1": "https://www.taobao.com",
                "actionTitle2": "两个按钮",
                "actionURL2": "https://www.tmall.com"
              }
            }
            """;
    String ROBOT_ACTION_CARD_3_PARAM = """
            {
              "pushSubject": "robot",
              "userIdType": "userIds 或者 openConversationId",
              "msgParam": {
                "title": "消息标题测试",
                "text": "消息内容测试",
                "actionTitle1": "第一个按钮的文本",
                "actionURL1": "第一个按钮触发的url",
                "actionTitle2": "第二个按钮的文本",
                "actionURL2": "第二个按钮触发的url",
                "actionTitle3": "第三个按钮的文本",
                "actionURL3": "第三个按钮触发的url"
              }
            }
            """;
    String ROBOT_ACTION_CARD_4_PARAM = """
            {
              "pushSubject": "robot",
              "userIdType": "userIds 或者 openConversationId",
              "msgParam": {
                "title":"消息标题测试",
                "text":"消息内容测试",
                "actionTitle1":"第一个按钮的文本",
                "actionURL1":"第一个按钮触发的url",
                "actionTitle2":"第二个按钮的文本",
                "actionURL2":"第二个按钮触发的url",
                "actionTitle3":"第三个按钮的文本",
                "actionURL3":"第三个按钮触发的url",
                "actionTitle4":"第四个按钮的文本",
                "actionURL4":"第四个按钮触发的url"
              }
            }
            """;
    String ROBOT_ACTION_CARD_5_PARAM = """
            {
              "pushSubject": "robot",
              "userIdType": "userIds 或者 openConversationId",
              "msgParam": {
                "title":"消息标题测试",
                "text":"消息内容测试",
                "actionTitle1":"第一个按钮的文本",
                "actionURL1":"第一个按钮触发的url",
                "actionTitle2":"第二个按钮的文本",
                "actionURL2":"第二个按钮触发的url",
                "actionTitle3":"第三个按钮的文本",
                "actionURL3":"第三个按钮触发的url",
                "actionTitle4":"第四个按钮的文本",
                "actionURL4":"第四个按钮触发的url",
                "actionTitle5":"第五个按钮的文本",
                "actionURL5":"第五个按钮触发的url"
              }
            }
            """;
    String ROBOT_ACTION_CARD_6_PARAM = """
            {
              "pushSubject": "robot",
              "userIdType": "userIds 或者 openConversationId",
              "msgParam": {
                "title": "xxxx",
                "text": "xxxx",
                "buttonTitle1":"xxxxx",
                "buttonUrl1":"xxxxx",
                "buttonTitle2":"xxxxx",
                "buttonUrl2":"xxxxx"
              }
            }
            """;
    String ROBOT_AUDIO_PARAM = """
            {
              "pushSubject": "robot",
              "userIdType": "userIds 或者 openConversationId",
              "msgParam": {
                "mediaId": "@IR_P********nFkfhsisbf4A",
                "duration":"xxxxx"
              }
            }
            """;
    String ROBOT_FILE_PARAM = """
            {
              "pushSubject": "robot",
              "userIdType": "userIds 或者 openConversationId",
              "msgParam": {
                "mediaId":"@lAz*********shRs5m2pRL",
                "fileName":"表格.xlsx",
                "fileType":"xlsx"
              }
            }
            """;
    String ROBOT_VIDEO_PARAM = """
            {
              "pushSubject": "robot",
              "userIdType": "userIds 或者 openConversationId",
              "msgParam": {
                "duration":"999",
                "videoMediaId":"$iQEL**********AXNSs6QPPAAABhKN2L3EEzTcTB84BFM7iCAAJomlt",
                "videoType":"mp4",
                "picMediaId":"$igE**********tAFzQUABtoAIYQBpAtR3SICqrQ7ZX0nEJzAKxUDzwAAAYSjdi2cBM02vwcACAAJomltCgQ"
              }
            }
            """;
}
