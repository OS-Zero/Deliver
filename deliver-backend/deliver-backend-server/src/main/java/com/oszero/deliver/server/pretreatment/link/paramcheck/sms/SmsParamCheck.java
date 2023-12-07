package com.oszero.deliver.server.pretreatment.link.paramcheck.sms;

import com.oszero.deliver.server.model.dto.SendTaskDto;
import com.oszero.deliver.server.pretreatment.link.BusinessLink;
import com.oszero.deliver.server.pretreatment.link.LinkContext;
import com.oszero.deliver.server.util.MessageLinkTraceUtils;
import org.springframework.stereotype.Service;

/**
 * 短信消息参数校验
 *
 * @author oszero
 * @version 1.0.0
 */
@Service
public class SmsParamCheck implements BusinessLink<SendTaskDto> {
    @Override
    public void process(LinkContext<SendTaskDto> context) {
        SendTaskDto sendTaskDto = context.getProcessModel();

        MessageLinkTraceUtils.recordMessageLifecycleInfoLog(sendTaskDto, "完成短信消息参数校验");
    }
}
