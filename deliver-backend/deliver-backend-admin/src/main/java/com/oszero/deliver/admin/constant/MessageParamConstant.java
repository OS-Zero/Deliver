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

        // 邮件消息参数
        MESSAGE_PARAM_MAP.put(ChannelTypeEnum.MAIL.getCode() + H + MessageTypeEnum.TEXT.getCode(), "{\n" +
                "  \"title\": \"\",\n" +
                "  \"content\": \"\",\n" +
                "  \"toCC\": \"\",\n" +
                "  \"toBCC\": \"\"\n" +
                "}");

        // 钉钉消息参数
        MESSAGE_PARAM_MAP.put(ChannelTypeEnum.DING.getCode() + H + MessageTypeEnum.TEXT.getCode(), "{\n" +
                "\t\t\"dingUserIdType\": \"userid_list 或者 dept_id_list 或者 userIds 或者 openConversationId\",\n" +
                "\t\t\"pushSubject\": \"workNotice 或者 robot\",\n" +
                "\t\t\"msgParam\": {\n" +
                "\t\t\t\"content\": \"xxxx\"\n" +
                "\t\t},\n" +
                "\t\t\"msg\": {\n" +
                "\t\t\t\"text\": {\n" +
                "\t\t\t\t\"content\": \"月会通知\"\n" +
                "\t\t\t}\n" +
                "\t\t}\n" +
                "\t}");
        MESSAGE_PARAM_MAP.put(ChannelTypeEnum.DING.getCode() + H + MessageTypeEnum.DING_IMAGE.getCode(), "{\n" +
                "\t\t\"dingUserIdType\": \"userid_list 或者 dept_id_list 或者 userIds 或者 openConversationId\",\n" +
                "\t\t\"pushSubject\": \"workNotice 或者 robot\",\n" +
                "\t\t\"msgParam\": {\n" +
                "\t\t\t\"photoURL\": \"xxxx\"\n" +
                "\t\t},\n" +
                "\t\t\"msg\": {\n" +
                "\t\t\t\"image\": {\n" +
                "\t\t\t\t\"media_id\": \"@lADOADmaWMzazQKA\"\n" +
                "\t\t\t}\n" +
                "\t\t}\n" +
                "\t}");
        MESSAGE_PARAM_MAP.put(ChannelTypeEnum.DING.getCode() + H + MessageTypeEnum.DING_VOICE.getCode(), "{\n" +
                "\t\t\"dingUserIdType\": \"userid_list 或者 dept_id_list 或者 userIds 或者 openConversationId\",\n" +
                "\t\t\"pushSubject\": \"workNotice 或者 robot\",\n" +
                "\t\t\"msgParam\": {\n" +
                "\t\t\t\"mediaId\": \"@IR_P********nFkfhsisbf4A\",\n" +
                "\t\t\t\"duration\": \"xxxxx\"\n" +
                "\t\t},\n" +
                "\t\t\"msg\": {\n" +
                "\t\t\t\"voice\": {\n" +
                "\t\t\t\t\"media_id\": \"@lADOADmaWMzazQKA\",\n" +
                "\t\t\t\t\"duration\": \"10\"\n" +
                "\t\t\t}\n" +
                "\t\t}\n" +
                "\t}");
        MESSAGE_PARAM_MAP.put(ChannelTypeEnum.DING.getCode() + H + MessageTypeEnum.DING_FILE.getCode(), "{\n" +
                "\t\t\"dingUserIdType\": \"userid_list 或者 dept_id_list 或者 userIds 或者 openConversationId\",\n" +
                "\t\t\"pushSubject\": \"workNotice 或者 robot\",\n" +
                "\t\t\"msgParam\": {\n" +
                "\t\t\t\"mediaId\": \"@lAz*********shRs5m2pRL\",\n" +
                "\t\t\t\"fileName\": \"表格.xlsx\",\n" +
                "\t\t\t\"fileType\": \"xlsx\"\n" +
                "\t\t},\n" +
                "\t\t\"msg\": {\n" +
                "\t\t\t\"file\": {\n" +
                "\t\t\t\t\"media_id\": \"MEDIA_ID\"\n" +
                "\t\t\t}\n" +
                "\t\t}\n" +
                "\t}");
        MESSAGE_PARAM_MAP.put(ChannelTypeEnum.DING.getCode() + H + MessageTypeEnum.DING_LINK.getCode(), "{\n" +
                "\t\t\"dingUserIdType\": \"userid_list 或者 dept_id_list 或者 userIds 或者 openConversationId\",\n" +
                "\t\t\"pushSubject\": \"workNotice 或者 robot\",\n" +
                "\t\t\"msgParam\": {\n" +
                "\t\t\t\"text\": \"消息内容测试\",\n" +
                "\t\t\t\"title\": \"sampleLink消息测试\",\n" +
                "\t\t\t\"picUrl\": \"@lADOADmaWMzazQKA\",\n" +
                "\t\t\t\"messageUrl\": \"http://dingtalk.com\"\n" +
                "\t\t},\n" +
                "\t\t\"msg\": {\n" +
                "\t\t\t\"link\": {\n" +
                "\t\t\t\t\"messageUrl\": \"http://s.dingtalk.com/market/dingtalk/error_code.php\",\n" +
                "\t\t\t\t\"picUrl\": \"@lALOACZwe2Rk\",\n" +
                "\t\t\t\t\"title\": \"测试\",\n" +
                "\t\t\t\t\"text\": \"测试\"\n" +
                "\t\t\t}\n" +
                "\t\t}\n" +
                "\t}");
        MESSAGE_PARAM_MAP.put(ChannelTypeEnum.DING.getCode() + H + MessageTypeEnum.DING_OA.getCode(), "{\n" +
                "\t\t\"dingUserIdType\": \"userid_list 或者 dept_id_list\",\n" +
                "\t\t\"pushSubject\": \"workNotice\",\n" +
                "\t\t\"msg\": {\n" +
                "\t\t\t\"oa\": {\n" +
                "\t\t\t\t\"message_url\": \"http://dingtalk.com\",\n" +
                "\t\t\t\t\"head\": {\n" +
                "\t\t\t\t\t\"bgcolor\": \"FFBBBBBB\",\n" +
                "\t\t\t\t\t\"text\": \"头部标题\"\n" +
                "\t\t\t\t},\n" +
                "\t\t\t\t\"body\": {\n" +
                "\t\t\t\t\t\"title\": \"正文标题\",\n" +
                "\t\t\t\t\t\"form\": [\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"key\": \"姓名:\",\n" +
                "\t\t\t\t\t\t\t\"value\": \"张三\"\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"key\": \"年龄:\",\n" +
                "\t\t\t\t\t\t\t\"value\": \"20\"\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"key\": \"身高:\",\n" +
                "\t\t\t\t\t\t\t\"value\": \"1.8米\"\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"key\": \"体重:\",\n" +
                "\t\t\t\t\t\t\t\"value\": \"130斤\"\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"key\": \"学历:\",\n" +
                "\t\t\t\t\t\t\t\"value\": \"本科\"\n" +
                "\t\t\t\t\t\t},\n" +
                "\t\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\t\"key\": \"爱好:\",\n" +
                "\t\t\t\t\t\t\t\"value\": \"打球、听音乐\"\n" +
                "\t\t\t\t\t\t}\n" +
                "\t\t\t\t\t],\n" +
                "\t\t\t\t\t\"rich\": {\n" +
                "\t\t\t\t\t\t\"num\": \"15.6\",\n" +
                "\t\t\t\t\t\t\"unit\": \"元\"\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t\"content\": \"大段文本大段文本大段文本大段文本大段文本大段文本\",\n" +
                "\t\t\t\t\t\"image\": \"@lADOADmaWMzazQKA\",\n" +
                "\t\t\t\t\t\"file_count\": \"3\",\n" +
                "\t\t\t\t\t\"author\": \"李四 \"\n" +
                "\t\t\t\t}\n" +
                "\t\t\t}\n" +
                "\t\t}\n" +
                "\t}");
        MESSAGE_PARAM_MAP.put(ChannelTypeEnum.DING.getCode() + H + MessageTypeEnum.DING_MARKDOWN.getCode(), "{\n" +
                "\t\t\"dingUserIdType\": \"userid_list 或者 dept_id_list 或者 userIds 或者 openConversationId\",\n" +
                "\t\t\"pushSubject\": \"workNotice 或者 robot\",\n" +
                "\t\t\"msgParam\": {\n" +
                "\t\t\t\"title\": \"xxxx\",\n" +
                "\t\t\t\"text\": \"xxxx\"\n" +
                "\t\t},\n" +
                "\t\t\"msg\": {\n" +
                "\t\t\t\"markdown\": {\n" +
                "\t\t\t\t\"title\": \"首屏会话透出的展示内容\",\n" +
                "\t\t\t\t\"text\": \"# 这是支持markdown的文本   \\n   ## 标题2    \\n   * 列表1   \\n  ![alt 啊](https://img.alicdn.com/tps/TB1XLjqNVXXXXc4XVXXXXXXXXXX-170-64.png)\"\n" +
                "\t\t\t}\n" +
                "\t\t}\n" +
                "\t}");
        MESSAGE_PARAM_MAP.put(ChannelTypeEnum.DING.getCode() + H + MessageTypeEnum.DING_CARD.getCode(), "{\n" +
                "\t\t\"dingUserIdType\": \"userid_list 或者 dept_id_list 或者 userIds 或者 openConversationId\",\n" +
                "\t\t\"pushSubject\": \"workNotice 或者 robot\",\n" +
                "\t\t\"msgParam\": {\n" +
                "\t\t\t\"title\": \"测试标题\",\n" +
                "\t\t\t\"text\": \"内容测试\",\n" +
                "\t\t\t\"singleTitle\": \"查看详情\",\n" +
                "\t\t\t\"singleURL\": \"https://open.dingtalk.com\"\n" +
                "\t\t},\n" +
                "\t\t\"msg\": {\n" +
                "\t\t\t\"action_card\": {\n" +
                "\t\t\t\t\"title\": \"是透出到会话列表和通知的文案\",\n" +
                "\t\t\t\t\"markdown\": \"支持markdown格式的正文内容\",\n" +
                "\t\t\t\t\"single_title\": \"查看详情\",\n" +
                "\t\t\t\t\"single_url\": \"https://open.dingtalk.com\"\n" +
                "\t\t\t}\n" +
                "\t\t}\n" +
                "\t}");

        // 飞书消息参数
        MESSAGE_PARAM_MAP.put(ChannelTypeEnum.FEI_SHU.getCode() + H + MessageTypeEnum.TEXT.getCode(), "{\n" +
                "  \"feiShuUserIdType\": \"\",\n" +
                "  \"content\": {\n" +
                "    \"text\": \"\"\n" +
                "  }\n" +
                "}");
        MESSAGE_PARAM_MAP.put(ChannelTypeEnum.FEI_SHU.getCode() + H + MessageTypeEnum.FEI_SHU_POST.getCode(), "{\n" +
                "  \"feiShuUserIdType\": \"\",\n" +
                "  \"content\": {\n" +
                "    \"post\": {\n" +
                "      \"zh_cn\": {\n" +
                "        \"title\": \"\",\n" +
                "        \"content\": []\n" +
                "      },\n" +
                "      \"en_us\": {\n" +
                "        \"title\": \"\",\n" +
                "        \"content\": []\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}");
        MESSAGE_PARAM_MAP.put(ChannelTypeEnum.FEI_SHU.getCode() + H + MessageTypeEnum.FEI_SHU_IMAGE.getCode(), "{\n" +
                "  \"feiShuUserIdType\": \"\",\n" +
                "  \"content\": {\n" +
                "    \"image_key\": \"\"\n" +
                "  }\n" +
                "}");
        MESSAGE_PARAM_MAP.put(ChannelTypeEnum.FEI_SHU.getCode() + H + MessageTypeEnum.FEI_SHU_INTERACTIVE.getCode(), "{\n" +
                "  \"feiShuUserIdType\": \"\",\n" +
                "  \"card\": {}\n" +
                "}");
        MESSAGE_PARAM_MAP.put(ChannelTypeEnum.FEI_SHU.getCode() + H + MessageTypeEnum.FEI_SHU_SHARE_CHAT.getCode(), "{\n" +
                "  \"feiShuUserIdType\": \"\",\n" +
                "  \"content\": {\n" +
                "    \"chat_id\": \"\"\n" +
                "  }\n" +
                "}");
        MESSAGE_PARAM_MAP.put(ChannelTypeEnum.FEI_SHU.getCode() + H + MessageTypeEnum.FEI_SHU_SHARE_USER.getCode(), "{\n" +
                "  \"feiShuUserIdType\": \"\",\n" +
                "  \"content\": {\n" +
                "    \"user_id\": \"\"\n" +
                "  }\n" +
                "}");
        MESSAGE_PARAM_MAP.put(ChannelTypeEnum.FEI_SHU.getCode() + H + MessageTypeEnum.FEI_SHU_AUDIO.getCode(), "{\n" +
                "  \"feiShuUserIdType\": \"\",\n" +
                "  \"content\": {\n" +
                "    \"file_key\": \"\"\n" +
                "  }\n" +
                "}");
        MESSAGE_PARAM_MAP.put(ChannelTypeEnum.FEI_SHU.getCode() + H + MessageTypeEnum.FEI_SHU_MEDIA.getCode(), "{\n" +
                "  \"feiShuUserIdType\": \"\",\n" +
                "  \"content\": {\n" +
                "    \"file_key\": \"\",\n" +
                "    \"image_key\": \"\"\n" +
                "  }\n" +
                "}");
        MESSAGE_PARAM_MAP.put(ChannelTypeEnum.FEI_SHU.getCode() + H + MessageTypeEnum.FEI_SHU_FILE.getCode(), "{\n" +
                "  \"feiShuUserIdType\": \"\",\n" +
                "  \"content\": {\n" +
                "    \"file_key\": \"\"\n" +
                "  }\n" +
                "}");
        MESSAGE_PARAM_MAP.put(ChannelTypeEnum.FEI_SHU.getCode() + H + MessageTypeEnum.FEI_SHU_STICKER.getCode(), "{\n" +
                "  \"feiShuUserIdType\": \"\",\n" +
                "  \"content\": {\n" +
                "    \"file_key\": \"\"\n" +
                "  }\n" +
                "}");
    }
}
