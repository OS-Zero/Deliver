package com.oszero.deliver.server.pretreatment.link.paramcheck.ding;

import com.oszero.deliver.server.model.dto.SendTaskDto;
import com.oszero.deliver.server.pretreatment.link.LinkContext;
import com.oszero.deliver.server.pretreatment.link.MessageLink;
import org.springframework.stereotype.Component;

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

    }
}
