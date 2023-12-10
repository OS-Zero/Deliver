package com.oszero.deliver.server.pretreatment.link.paramcheck.sms;

import com.oszero.deliver.server.model.dto.common.SendTaskDto;
import com.oszero.deliver.server.pretreatment.common.MessageLink;
import com.oszero.deliver.server.pretreatment.common.LinkContext;
import com.oszero.deliver.server.util.MessageLinkTraceUtils;
import org.springframework.stereotype.Service;

/**
 * 短信消息参数校验
 *
 * @author oszero
 * @version 1.0.0
 */
@Service
public class SmsParamCheck implements MessageLink<SendTaskDto> {
    @Override
    public void process(LinkContext<SendTaskDto> context) {
        SendTaskDto sendTaskDto = context.getProcessModel();

        MessageLinkTraceUtils.recordMessageLifecycleInfoLog(sendTaskDto, "完成短信消息参数校验");
    }
}
