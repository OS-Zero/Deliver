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
