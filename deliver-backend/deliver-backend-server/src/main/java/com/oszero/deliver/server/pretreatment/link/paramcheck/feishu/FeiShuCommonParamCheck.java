package com.oszero.deliver.server.pretreatment.link.paramcheck.feishu;

import cn.hutool.core.util.StrUtil;
import com.oszero.deliver.server.enums.MessageTypeEnum;
import com.oszero.deliver.server.exception.MessageException;
import com.oszero.deliver.server.model.dto.SendTaskDto;
import com.oszero.deliver.server.pretreatment.link.LinkContext;
import com.oszero.deliver.server.pretreatment.link.MessageLink;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 飞书通用参数校验
 *
 * @author oszero
 * @version 1.0.0
 */
@Service
public class FeiShuCommonParamCheck implements MessageLink<SendTaskDto> {
    private static final Set<String> FEI_SHU_USER_ID_TYPE_SET = new HashSet<>(
            Arrays.asList("user_id ", "email", "chat_id ", "department_id"));
    /**
     * 可以通过 department_id 发送的消息类型
     */
    private static final Set<String> DEPARTMENT_MESSAGE_TYPE =
            new HashSet<>(Arrays.asList(
                    MessageTypeEnum.TEXT.getCode(),
                    MessageTypeEnum.FEI_SHU_POST.getCode(),
                    MessageTypeEnum.FEI_SHU_IMAGE.getCode(),
                    MessageTypeEnum.FEI_SHU_INTERACTIVE.getCode(),
                    MessageTypeEnum.FEI_SHU_SHARE_CHAT.getCode()));

    @Override
    public void process(LinkContext<SendTaskDto> context) {
        SendTaskDto sendTaskDto = context.getProcessModel();
        String messageType = sendTaskDto.getMessageType();
        Map<String, Object> paramMap = sendTaskDto.getParamMap();
        String feiShuUserIdType = paramMap.getOrDefault("feiShuUserIdType", "").toString();
        if (StrUtil.isBlank(feiShuUserIdType)) {
            throw new MessageException(sendTaskDto, "飞书 feiShuUserIdType 参数为空");
        }
        if (!FEI_SHU_USER_ID_TYPE_SET.contains(feiShuUserIdType)) {
            throw new MessageException(sendTaskDto, "飞书 feiShuUserIdType 参数非法，必须为 user_id 或者 email 或者 chat_id 或者 department_id");
        }
        if ("department_id".equals(feiShuUserIdType) && !DEPARTMENT_MESSAGE_TYPE.contains(messageType)) {
            throw new MessageException(sendTaskDto, "飞书 feiShuUserIdType 为 department_id 时，不支持此消息类型");
        }
    }
}
