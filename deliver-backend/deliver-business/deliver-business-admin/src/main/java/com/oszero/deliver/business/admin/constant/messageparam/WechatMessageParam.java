package com.oszero.deliver.business.admin.constant.messageparam;

/**
 * @author oszero
 * @version 1.0.0
 */
public interface WechatMessageParam {
    // 企微应用消息
    String APP_TEXT_PARAM = """
            {
              "userIdType": "touser 或者 toparty 或者 totag",
              "text": {
                "content": "你的快递已到，请携带工卡前往邮件中心领取。\\n出发前可查看<a href=\\"https://work.weixin.qq.com\\">邮件中心视频实况</a>，聪明避开排队。"
              },
              "safe": 0,
              "enable_id_trans": 0,
              "enable_duplicate_check": 0,
              "duplicate_check_interval": 1800
            }
            """;
    String APP_IMAGE_PARAM = """
            {
              "userIdType": "touser 或者 toparty 或者 totag",
              "image": {
                "media_id": "MEDIA_ID"
              },
              "safe": 0,
              "enable_duplicate_check": 0,
              "duplicate_check_interval": 1800
            }
            """;
    String APP_VOICE_PARAM = """
            {
              "userIdType": "touser 或者 toparty 或者 totag",
              "voice": {
                "media_id": "MEDIA_ID"
              },
              "enable_duplicate_check": 0,
              "duplicate_check_interval": 1800
            }
            """;
    String APP_VIDEO_PARAM = """
            {
              "userIdType": "touser 或者 toparty 或者 totag",
              "video": {
                "media_id": "MEDIA_ID",
                "title": "Title",
                "description": "Description"
              },
              "safe": 0,
              "enable_duplicate_check": 0,
              "duplicate_check_interval": 1800
            }
            """;
    String APP_FILE_PARAM = """
            {
              "userIdType": "touser 或者 toparty 或者 totag",
              "file": {
                "media_id": "1Yv-zXfHjSjU-7LH-GwtYqDGS-zz6w22KmWAT5COgP7o"
              },
              "safe": 0,
              "enable_duplicate_check": 0,
              "duplicate_check_interval": 1800
            }
            """;
    String APP_TEXT_CARD_PARAM = """
            {
              "userIdType": "touser 或者 toparty 或者 totag",
              "textcard": {
                "title": "领奖通知",
                "description": "<div class=\\"gray\\">2016年9月26日</div> <div class=\\"normal\\">恭喜你抽中iPhone 7一台，领奖码：xxxx</div><div class=\\"highlight\\">请于2016年10月10日前联系行政同事领取</div>",
                "url": "URL",
                "btntxt": "更多"
              },
              "enable_id_trans": 0,
              "enable_duplicate_check": 0,
              "duplicate_check_interval": 1800
            }
            """;
    String APP_NEWS_PARAM = """
            {
              "userIdType": "touser 或者 toparty 或者 totag",
              "news": {
                "articles": [
                  {
                    "title": "中秋节礼品领取",
                    "description": "今年中秋节公司有豪礼相送",
                    "url": "URL",
                    "picurl": "https://res.mail.qq.com/node/ww/wwopenmng/images/independent/doc/test_pic_msg1.png",
                    "appid": "wx123123123123123",
                    "pagepath": "pages/index?userid=zhangsan&orderid=123123123"
                  }
                ]
              },
              "enable_id_trans": 0,
              "enable_duplicate_check": 0,
              "duplicate_check_interval": 1800
            }
            """;
    String APP_MPNEWS_PARAM = """
            {
              "userIdType": "touser 或者 toparty 或者 totag",
              "mpnews": {
                "articles": [
                  {
                    "title": "Title",
                    "thumb_media_id": "MEDIA_ID",
                    "author": "Author",
                    "content_source_url": "URL",
                    "content": "Content",
                    "digest": "Digest description"
                  }
                ]
              },
              "safe": 0,
              "enable_id_trans": 0,
              "enable_duplicate_check": 0,
              "duplicate_check_interval": 1800
            }
            """;
    String APP_MARKDOWN_PARAM = """
            {
              "userIdType": "touser 或者 toparty 或者 totag",
              "markdown": {
                "content": "您的会议室已经预定，稍后会同步到`邮箱`  \\n>**事项详情**  \\n>事　项：<font color=\\"info\\">开会</font>  \\n>组织者：@miglioguan  \\n>参与者：@miglioguan、@kunliu、@jamdeezhou、@kanexiong、@kisonwang  \\n>  \\n>会议室：<font color=\\"info\\">广州TIT 1楼 301</font>  \\n>日　期：<font color=\\"warning\\">2018年5月18日</font>  \\n>时　间：<font color=\\"comment\\">上午9:00-11:00</font>  \\n>  \\n>请准时参加会议。  \\n>  \\n>如需修改会议信息，请点击：[修改会议信息](https://work.weixin.qq.com)"
              },
              "enable_duplicate_check": 0,
              "duplicate_check_interval": 1800
            }
            """;
    String APP_MINIPROGRAM_NOTICE_PARAM = """
            {
              "userIdType": "touser 或者 toparty 或者 totag",
              "miniprogram_notice": {
                "appid": "wx123123123123123",
                "page": "pages/index?userid=zhangsan&orderid=123123123",
                "title": "会议室预订成功通知",
                "description": "4月27日 16:16",
                "emphasis_first_item": true,
                "content_item": [
                  {
                    "key": "会议室",
                    "value": "402"
                  },
                  {
                    "key": "会议地点",
                    "value": "广州TIT-402会议室"
                  },
                  {
                    "key": "会议时间",
                    "value": "2018年8月1日 09:00-09:30"
                  },
                  {
                    "key": "参与人员",
                    "value": "周剑轩"
                  }
                ]
              },
              "enable_id_trans": 0,
              "enable_duplicate_check": 0,
              "duplicate_check_interval": 1800
            }
            """;
    String APP_TEMPLATE_CARD_PARAM = """
            {
              "userIdType": "touser 或者 toparty 或者 totag",
              "template_card": {
                "card_type": "text_notice",
                "source": {
                  "icon_url": "图片的url",
                  "desc": "企业微信",
                  "desc_color": 1
                },
                "action_menu": {
                  "desc": "卡片副交互辅助文本说明",
                  "action_list": [
                    {
                      "text": "接受推送",
                      "key": "A"
                    },
                    {
                      "text": "不再推送",
                      "key": "B"
                    }
                  ]
                },
                "task_id": "task_id",
                "main_title": {
                  "title": "欢迎使用企业微信",
                  "desc": "您的好友正在邀请您加入企业微信"
                },
                "quote_area": {
                  "type": 1,
                  "url": "https://work.weixin.qq.com",
                  "title": "企业微信的引用样式",
                  "quote_text": "企业微信真好用呀真好用"
                },
                "emphasis_content": {
                  "title": "100",
                  "desc": "核心数据"
                },
                "sub_title_text": "下载企业微信还能抢红包！",
                "horizontal_content_list": [
                  {
                    "keyname": "邀请人",
                    "value": "张三"
                  },
                  {
                    "type": 1,
                    "keyname": "企业微信官网",
                    "value": "点击访问",
                    "url": "https://work.weixin.qq.com"
                  },
                  {
                    "type": 2,
                    "keyname": "企业微信下载",
                    "value": "企业微信.apk",
                    "media_id": "文件的media_id"
                  },
                  {
                    "type": 3,
                    "keyname": "员工信息",
                    "value": "点击查看",
                    "userid": "zhangsan"
                  }
                ],
                "jump_list": [
                  {
                    "type": 1,
                    "title": "企业微信官网",
                    "url": "https://work.weixin.qq.com"
                  },
                  {
                    "type": 2,
                    "title": "跳转小程序",
                    "appid": "小程序的appid",
                    "pagepath": "/index.html"
                  }
                ],
                "card_action": {
                  "type": 2,
                  "url": "https://work.weixin.qq.com",
                  "appid": "小程序的appid",
                  "pagepath": "/index.html"
                }
              },
              "enable_id_trans": 0,
              "enable_duplicate_check": 0,
              "duplicate_check_interval": 1800
            }
            """;
    // 企微应用消息到群聊会话
    String APP_TO_GROUP_TEXT_PARAM = """
            {
              "userIdType": "chatid",
              "text": {
                "content": "你的快递已到\\n请携带工卡前往邮件中心领取"
              },
              "safe": 0
            }
            """;
    String APP_TO_GROUP_IMAGE_PARAM = """
            {
              "userIdType": "chatid",
              "image": {
                "media_id": "MEDIAID"
              },
              "safe": 0
            }
            """;
    String APP_TO_GROUP_VOICE_PARAM = """
            {
              "userIdType": "chatid",
              "voice": {
                "media_id": "MEDIA_ID"
              }
            }
            """;
    String APP_TO_GROUP_VIDEO_PARAM = """
            {
              "userIdType": "chatid",
              "video": {
                "media_id": "MEDIA_ID",
                "description": "Description",
                "title": "Title"
              },
              "safe": 0
            }
            """;
    String APP_TO_GROUP_FILE_PARAM = """
            {
              "userIdType": "chatid",
              "file": {
                "media_id": "1Yv-zXfHjSjU-7LH-GwtYqDGS-zz6w22KmWAT5COgP7o"
              },
              "safe": 0
            }
            """;
    String APP_TO_GROUP_TEXT_CARD_PARAM = """
            {
              "userIdType": "chatid",
              "textcard": {
                "title": "领奖通知",
                "description": "<div class=\\"gray\\">2016年9月26日</div> <div class=\\"normal\\"> 恭喜你抽中iPhone 7一台，领奖码:520258</div><div class=\\"highlight\\">请于2016年10月10日前联系行 政同事领取</div>",
                "url": "https://work.weixin.qq.com/",
                "btntxt": "更多"
              },
              "safe": 0
            }
            """;
    String APP_TO_GROUP_NEWS_PARAM = """
            {
              "userIdType": "chatid",
              "news": {
                "articles": [
                  {
                    "title": "中秋节礼品领取",
                    "description": "今年中秋节公司有豪礼相送",
                    "url": "https://work.weixin.qq.com/",
                    "picurl": "http://res.mail.qq.com/node/ww/wwopenmng/images/independent/doc/test_pic_msg1.png"
                  }
                ]
              },
              "safe": 0
            }
            """;
    String APP_TO_GROUP_MPNEWS_PARAM = """
            {
              "userIdType": "chatid",
              "mpnews": {
                "articles": [
                  {
                    "title": "地球一小时",
                    "thumb_media_id": "biz_get(image)",
                    "author": "Author",
                    "content_source_url": "https://work.weixin.qq.com",
                    "content": "3月24日20:30-21:30 \\n办公区将关闭照明一小时，请各部门同事相互转告",
                    "digest": "3月24日20:30-21:30 \\n办公区将关闭照明一小时"
                  }
                ]
              },
              "safe": 0
            }
            """;
    String APP_TO_GROUP_MARKDOWN_PARAM = """
            {
              "userIdType": "chatid",
              "markdown": {
                "content": "您的会议室已经预定，稍后会同步到`邮箱`  \\n>**事项详情**  \\n>事　项：<font color=\\"info\\">开会</font>  \\n>组织者：@miglioguan  \\n>参与者：@miglioguan、@kunliu、@jamdeezhou、@kanexiong、@kisonwang  \\n>  \\n>会议室：<font color=\\"info\\">广州TIT 1楼 301</font>  \\n>日　期：<font color=\\"warning\\">2018年5月18日</font>  \\n>时　间：<font color=\\"comment\\">上午9:00-11:00</font>  \\n>  \\n>请准时参加会议。  \\n>  \\n>如需修改会议信息，请点击：[修改会议信息](https://work.weixin.qq.com)"
              }
            }
            """;
    // 企微家校消息
    String SCHOOL_TEXT_PARAM = """
            {
              "userIdType": "to_parent_userid 或者 to_student_userid 或者 to_party 或者 toall",
              "recv_scope": 0,
              "text": {
                "content": "你的快递已到，请携带工卡前往邮件中心领取。\\n出发前可查看<a href=\\"http://work.weixin.qq.com\\">邮件中心视频实况</a>，聪明避开排队。"
              },
              "enable_id_trans": 0,
              "enable_duplicate_check": 0,
              "duplicate_check_interval": 1800
            }
            """;
    String SCHOOL_IMAGE_PARAM = """
            {
              "userIdType": "to_parent_userid 或者 to_student_userid 或者 to_party 或者 toall",
              "recv_scope": 0,
              "image": {
                "media_id": "MEDIA_ID"
              },
              "enable_duplicate_check": 0,
              "duplicate_check_interval": 1800
            }
            """;
    String SCHOOL_VOICE_PARAM = """
            {
              "userIdType": "to_parent_userid 或者 to_student_userid 或者 to_party 或者 toall",
              "recv_scope": 0,
              "voice": {
                "media_id": "MEDIA_ID"
              },
              "enable_duplicate_check": 0,
              "duplicate_check_interval": 1800
            }
            """;
    String SCHOOL_VIDEO_PARAM = """
            {
              "userIdType": "to_parent_userid 或者 to_student_userid 或者 to_party 或者 toall",
              "recv_scope": 0,
              "video": {
                "media_id": "MEDIA_ID",
                "title": "Title",
                "description": "Description"
              },
              "enable_duplicate_check": 0,
              "duplicate_check_interval": 1800
            }
            """;
    String SCHOOL_FILE_PARAM = """
            {
              "userIdType": "to_parent_userid 或者 to_student_userid 或者 to_party 或者 toall",
              "recv_scope": 0,
              "file": {
                "media_id": "1Yv-zXfHjSjU-7LH-GwtYqDGS-zz6w22KmWAT5COgP7o"
              },
              "enable_duplicate_check": 0,
              "duplicate_check_interval": 1800
            }
            """;
    String SCHOOL_NEWS_PARAM = """
            {
              "userIdType": "to_parent_userid 或者 to_student_userid 或者 to_party 或者 toall",
              "recv_scope": 0,
              "news": {
                "articles": [
                  {
                    "title": "中秋节礼品领取",
                    "description": "今年中秋节公司有豪礼相送",
                    "url": "URL",
                    "picurl": "http://res.mail.qq.com/node/ww/wwopenmng/images/independent/doc/test_pic_msg1.png"
                  }
                ]
              },
              "enable_id_trans": 0,
              "enable_duplicate_check": 0,
              "duplicate_check_interval": 1800
            }
            """;
    String SCHOOL_MPNEWS_PARAM = """
            {
              "userIdType": "to_parent_userid 或者 to_student_userid 或者 to_party 或者 toall",
              "recv_scope": 0,
              "mpnews": {
                "articles": [
                  {
                    "title": "Title",
                    "thumb_media_id": "MEDIA_ID",
                    "author": "Author",
                    "content_source_url": "URL",
                    "content": "Content",
                    "digest": "Digest description"
                  }
                ]
              },
              "enable_id_trans": 0,
              "enable_duplicate_check": 0,
              "duplicate_check_interval": 1800
            }
            """;
    String SCHOOL_MINIPROGRAM_NOTICE_PARAM = """
            {
              "userIdType": "to_parent_userid 或者 to_student_userid 或者 to_party 或者 toall",
              "recv_scope": 0,
              "miniprogram": {
                "appid": "APPID",
                "title": "欢迎报名夏令营",
                "thumb_media_id": "MEDIA_ID",
                "pagepath": "PAGE_PATH"
              },
              "enable_id_trans": 0,
              "enable_duplicate_check": 0,
              "duplicate_check_interval": 1800
            }
            """;
    // 企微群机器人消息
    String GROUP_ROBOT_TEXT_PARAM = """
            {
              "userIdType": "webhook",
              "text": {
                "content": "广州今日天气：29度，大部分多云，降雨概率：60%",
                "mentioned_list": [
                  "wangqing",
                  "@all"
                ],
                "mentioned_mobile_list": [
                  "13800001111",
                  "@all"
                ]
              }
            }
            """;
    String GROUP_ROBOT_MARKDOWN_PARAM = """
            {
              "userIdType": "webhook",
              "markdown": {
                "content": "实时新增用户反馈<font color=\\"warning\\">132例</font>，请相关同事注意。\\n>类型:<font color=\\"comment\\">用户反馈</font>>普通用户反馈:<font color=\\"comment\\">117例</font>>VIP用户反馈:<font color=\\"comment\\">15例</font>"
              }
            }
            """;
    String GROUP_ROBOT_IMAGE_PARAM = """
            {
              "userIdType": "webhook",
              "image": {
                "base64": "DATA",
                "md5": "MD5"
              }
            }
            """;
    String GROUP_ROBOT_MPNEWS_PARAM = """
            {
               "userIdType": "webhook",
               "news": {
                 "articles": [
                   {
                     "title": "中秋节礼品领取",
                     "description": "今年中秋节公司有豪礼相送",
                     "url": "www.qq.com",
                     "picurl": "http://res.mail.qq.com/node/ww/wwopenmng/images/independent/doc/test_pic_msg1.png"
                   }
                 ]
               }
             }
            """;
    String GROUP_ROBOT_FILE_PARAM = """
            {
               "userIdType": "webhook",
               "file": {
                 "media_id": "3a8asd892asd8asd"
               }
            }
            """;
    String GROUP_ROBOT_VOICE_PARAM = """
            {
              "userIdType": "webhook",
              "voice": {
                "media_id": "MEDIA_ID"
              }
            }
            """;
    String GROUP_ROBOT_TEMPLATE_CARD_PARAM = """
            {
              "userIdType": "webhook",
              "template_card": {
                "card_type": "text_notice",
                "source": {
                  "icon_url": "https://wework.qpic.cn/wwpic/252813_jOfDHtcISzuodLa_1629280209/0",
                  "desc": "企业微信",
                  "desc_color": 0
                },
                "main_title": {
                  "title": "欢迎使用企业微信",
                  "desc": "您的好友正在邀请您加入企业微信"
                },
                "emphasis_content": {
                  "title": "100",
                  "desc": "数据含义"
                },
                "quote_area": {
                  "type": 1,
                  "url": "https://work.weixin.qq.com/?from=openApi",
                  "appid": "APPID",
                  "pagepath": "PAGEPATH",
                  "title": "引用文本标题",
                  "quote_text": "Jack：企业微信真的很好用~\\nBalian：超级好的一款软件！"
                },
                "sub_title_text": "下载企业微信还能抢红包！",
                "horizontal_content_list": [
                  {
                    "keyname": "邀请人",
                    "value": "张三"
                  },
                  {
                    "keyname": "企微官网",
                    "value": "点击访问",
                    "type": 1,
                    "url": "https://work.weixin.qq.com/?from=openApi"
                  },
                  {
                    "keyname": "企微下载",
                    "value": "企业微信.apk",
                    "type": 2,
                    "media_id": "MEDIAID"
                  }
                ],
                "jump_list": [
                  {
                    "type": 1,
                    "url": "https://work.weixin.qq.com/?from=openApi",
                    "title": "企业微信官网"
                  },
                  {
                    "type": 2,
                    "appid": "APPID",
                    "pagepath": "PAGEPATH",
                    "title": "跳转小程序"
                  }
                ],
                "card_action": {
                  "type": 1,
                  "url": "https://work.weixin.qq.com/?from=openApi",
                  "appid": "APPID",
                  "pagepath": "PAGEPATH"
                }
              }
            }
            """;
}
