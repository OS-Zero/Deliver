package com.oszero.deliver.business.admin.constant.messageparam;

/**
 * @author oszero
 * @version 1.0.0
 */
public interface FeiShuMessageParam {
    String TEXT_PARAM = """
            {
              "userIdType": "user_id 或者 email 或者 chat_id 或者 department_id",
              "content": {
                "text": "test content"
              }
            }
            """;
    String POST_PARAM = """
            {
              "userIdType": "user_id 或者 email 或者 chat_id 或者 department_id",
              "content": {
                "zh_cn": {
                  "title": "我是一个标题",
                  "content": [
                    [
                      {
                        "tag": "text",
                        "text": "第一行:",
                        "style": [
                          "bold",
                          "underline"
                        ]
                      },
                      {
                        "tag": "a",
                        "href": "http://www.feishu.cn",
                        "text": "超链接",
                        "style": [
                          "bold",
                          "italic"
                        ]
                      },
                      {
                        "tag": "at",
                        "user_id": "ou_1avnmsbv3k45jnk34j5",
                        "style": [
                          "lineThrough"
                        ]
                      }
                    ],
                    [
                      {
                        "tag": "text",
                        "text": "第二行:",
                        "style": [
                          "bold",
                          "underline"
                        ]
                      },
                      {
                        "tag": "text",
                        "text": "文本测试"
                      }
                    ],
                    [
                      {
                        "tag": "emotion",
                        "emoji_type": "SMILE"
                      }
                    ],
                    [
                      {
                        "tag": "hr"
                      }
                    ],
                    [
                      {
                        "tag": "code_block",
                        "language": "GO",
                        "text": "func main() int64 {\\n    return 0\\n}"
                      }
                    ],
                    [
                      {
                        "tag": "md",
                        "text": "**mention user:**<at user_id=\\"ou_xxxxxx\\">Tom</at>\\n**href:**[Open Platform](https://open.feishu.cn)\\n**code block:**\\n```GO\\nfunc main() int64 {\\n    return 0\\n}\\n```\\n**text styles:** **bold**, *italic*, ***bold and italic***, ~underline~,~~lineThrough~~\\n> quote content\\n\\n1. item1\\n    1. item1.1\\n    2. item2.2\\n2. item2\\n --- \\n- item1\\n    - item1.1\\n    - item2.2\\n- item2"
                      }
                    ]
                  ]
                }
              }
            }
            """;
    String IMAGE_PARAM = """
            {
              "userIdType": "user_id 或者 email 或者 chat_id 或者 department_id",
              "content": {
                "image_key": "img_7ea74629-9191-4176-998c-2e603c9c5e8g"
              }
            }
            """;
    String INTERACTIVE_PARAM = """
            {
              "userIdType": "user_id 或者 email 或者 chat_id 或者 department_id",
              "content": {
                "config": {
                  "wide_screen_mode": true
                },
                "elements": [
                  {
                    "alt": {
                      "content": "",
                      "tag": "plain_text"
                    },
                    "img_key": "img_7ea74629-9191-4176-998c-2e603c9c5e8g",
                    "tag": "img"
                  },
                  {
                    "tag": "div",
                    "text": {
                      "content": "你是否曾因为一本书而产生心灵共振，开始感悟人生？\\n你有哪些想极力推荐给他人的珍藏书单？\\n\\n加入 **4·23 飞书读书节**，分享你的**挚爱书单**及**读书笔记**，**赢取千元读书礼**！\\n\\n📬 填写问卷，晒出你的珍藏好书\\n😍 想知道其他人都推荐了哪些好书？马上[入群围观](https://open.feishu.cn/)\\n📝 用[读书笔记模板](https://open.feishu.cn/)（桌面端打开），记录你的心得体会\\n🙌 更有惊喜特邀嘉宾 4月12日起带你共读",
                      "tag": "lark_md"
                    }
                  },
                  {
                    "actions": [
                      {
                        "tag": "button",
                        "text": {
                          "content": "立即推荐好书",
                          "tag": "plain_text"
                        },
                        "type": "primary",
                        "url": "https://open.feishu.cn/"
                      },
                      {
                        "tag": "button",
                        "text": {
                          "content": "查看活动指南",
                          "tag": "plain_text"
                        },
                        "type": "default",
                        "url": "https://open.feishu.cn/"
                      }
                    ],
                    "tag": "action"
                  }
                ],
                "header": {
                  "template": "turquoise",
                  "title": {
                    "content": "📚晒挚爱好书，赢读书礼金",
                    "tag": "plain_text"
                  }
                }
              }
            }
            """;
    String SHARE_CHAT_PARAM = """
            {
              "userIdType": "user_id 或者 email 或者 chat_id 或者 department_id",
              "content": {
                "chat_id": "oc_0dd200d32fda15216d2c2ef1ddb32f76"
              }
            }
            """;
    String SHARE_USER_PARAM = """
            {
              "userIdType": "user_id 或者 email 或者 chat_id 或者 department_id",
              "content": {
                "user_id": "ou_0dd200d32fda15216d2c2ef1ddb32f76"
              }
            }
            """;
    String AUDIO_PARAM = """
            {
              "userIdType": "user_id 或者 email 或者 chat_id 或者 department_id",
              "content": {
                "file_key": "75235e0c-4f92-430a-a99b-8446610223cg"
              }
            }
            """;
    String MEDIA_PARAM = """
            {
              "userIdType": "user_id 或者 email 或者 chat_id 或者 department_id",
              "content": {
                "file_key": "75235e0c-4f92-430a-a99b-8446610223cg",
                "image_key": "img_xxxxxx"
              }
            }
            """;
    String FILE_PARAM = """
            {
              "userIdType": "user_id 或者 email 或者 chat_id 或者 department_id",
              "content": {
                "file_key": "75235e0c-4f92-430a-a99b-8446610223cg"
              }
            }
            """;
    String STICKER_PARAM = """
            {
              "userIdType": "user_id 或者 email 或者 chat_id 或者 department_id",
              "content": {
                "file_key": "75235e0c-4f92-430a-a99b-8446610223cg"
              }
            }
            """;
}
