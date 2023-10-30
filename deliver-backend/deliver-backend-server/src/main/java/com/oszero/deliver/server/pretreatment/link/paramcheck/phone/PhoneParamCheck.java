package com.oszero.deliver.server.pretreatment.link.paramcheck.phone;

import com.oszero.deliver.server.model.dto.SendTaskDto;
import com.oszero.deliver.server.pretreatment.link.BusinessLink;
import com.oszero.deliver.server.pretreatment.link.LinkContext;
import org.springframework.stereotype.Service;

/**
 * 电话消息参数校验
 *
 * @author oszero
 * @version 1.0.0
 */
@Service
public class PhoneParamCheck implements BusinessLink<SendTaskDto> {
    @Override
    public void process(LinkContext<SendTaskDto> context) {

    }
}
