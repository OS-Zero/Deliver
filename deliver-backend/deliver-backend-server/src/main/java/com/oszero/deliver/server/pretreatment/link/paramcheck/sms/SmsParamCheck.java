package com.oszero.deliver.server.pretreatment.link.paramcheck.sms;

import com.oszero.deliver.server.model.dto.SendTaskDto;
import com.oszero.deliver.server.pretreatment.link.BusinessLink;
import com.oszero.deliver.server.pretreatment.link.LinkContext;
import org.springframework.stereotype.Service;

@Service
public class SmsParamCheck implements BusinessLink<SendTaskDto> {
    @Override
    public void process(LinkContext<SendTaskDto> context) {

    }
}
