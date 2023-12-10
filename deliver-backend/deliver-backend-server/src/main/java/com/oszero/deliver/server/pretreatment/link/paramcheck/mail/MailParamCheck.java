package com.oszero.deliver.server.pretreatment.link.paramcheck.mail;

import com.oszero.deliver.server.exception.MessageException;
import com.oszero.deliver.server.model.dto.common.SendTaskDto;
import com.oszero.deliver.server.pretreatment.common.LinkContext;
import com.oszero.deliver.server.pretreatment.common.MessageLink;
import com.oszero.deliver.server.util.MessageLinkTraceUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 邮件消息参数校验
 *
 * @author oszero
 * @version 1.0.0
 */
@Service
public class MailParamCheck implements MessageLink<SendTaskDto> {

    @Override
    public void process(LinkContext<SendTaskDto> context) {
        SendTaskDto sendTaskDto = context.getProcessModel();

        try {
            Map<String, Object> paramMap = sendTaskDto.getParamMap();

            // TODO:参数校验
        } catch (Exception exception) {
            throw new MessageException(sendTaskDto, "邮件消息参数校验失败，" + exception.getMessage());
        }

        MessageLinkTraceUtils.recordMessageLifecycleInfoLog(sendTaskDto, "完成邮件消息参数校验");
    }
}
