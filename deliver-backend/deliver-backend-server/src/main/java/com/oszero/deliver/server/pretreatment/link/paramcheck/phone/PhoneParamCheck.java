package com.oszero.deliver.server.pretreatment.link.paramcheck.phone;

import com.oszero.deliver.server.model.dto.SendTaskDto;
import com.oszero.deliver.server.pretreatment.link.MessageLink;
import com.oszero.deliver.server.pretreatment.link.LinkContext;
import com.oszero.deliver.server.util.MessageLinkTraceUtils;
import org.springframework.stereotype.Service;

/**
 * 电话消息参数校验
 *
 * @author oszero
 * @version 1.0.0
 */
@Service
public class PhoneParamCheck implements MessageLink<SendTaskDto> {
    @Override
    public void process(LinkContext<SendTaskDto> context) {
        SendTaskDto sendTaskDto = context.getProcessModel();

        MessageLinkTraceUtils.recordMessageLifecycleInfoLog(sendTaskDto, "完成手机号打电话参数校验");
    }
}
