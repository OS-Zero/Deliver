package com.oszero.deliver.server.pretreatment.link.paramcheck.ding;

import cn.hutool.core.util.StrUtil;
import com.oszero.deliver.server.enums.MessageTypeEnum;
import com.oszero.deliver.server.exception.MessageException;
import com.oszero.deliver.server.model.dto.SendTaskDto;
import com.oszero.deliver.server.pretreatment.common.LinkContext;
import com.oszero.deliver.server.pretreatment.common.MessageLink;
import com.oszero.deliver.server.util.MessageLinkTraceUtils;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 钉钉通用参数校验
 *
 * @author oszero
 * @version 1.0.0
 */
@Component
public class DingCommonParamCheck implements MessageLink<SendTaskDto> {

    @Override
    public void process(LinkContext<SendTaskDto> context) {
        SendTaskDto sendTaskDto = context.getProcessModel();
        String messageType = sendTaskDto.getMessageType();
        Map<String, Object> paramMap = sendTaskDto.getParamMap();

        String pushSubject = paramMap.getOrDefault("pushSubject", "").toString();
        String dingUserIdType = paramMap.getOrDefault("dingUserIdType", "").toString();

        if (StrUtil.isBlank(pushSubject) || StrUtil.isBlank(dingUserIdType)) {
            throw new MessageException(sendTaskDto, "钉钉 pushSubject 或者 dingUserIdType 参数为空");
        }

        if (!("workNotice".equals(pushSubject) || "robot".equals(pushSubject))) {
            throw new MessageException(sendTaskDto, "钉钉 pushSubject 非法，必须为 workNotice 或者 robot");
        }

        if ("workNotice".equals(pushSubject)) {
            if (!("userid_list".equals(dingUserIdType) || "dept_id_list".equals(dingUserIdType))) {
                throw new MessageException(sendTaskDto, "当 pushSubject 为 workNotice 时，dingUserIdType 必须为 userid_list 或者 dept_id_list");
            }
        } else {
            if (!("userIds".equals(dingUserIdType) || "openConversationId".equals(dingUserIdType))) {
                throw new MessageException(sendTaskDto, "当 pushSubject 为 robot 时，dingUserIdType 必须为 userIds 或者 openConversationId");
            }

            if (MessageTypeEnum.DING_OA.getCode().equals(messageType)) {
                throw new MessageException(sendTaskDto, "钉钉 pushSubject 为 robot 时，不支持 OA 消息类型");
            }
        }

        MessageLinkTraceUtils.recordMessageLifecycleInfoLog(sendTaskDto, "完成钉钉通用参数校验");
    }
}
