package com.oszero.deliver.server.pretreatment.link.paramcheck.wechat;

import com.oszero.deliver.server.model.dto.SendTaskDto;
import com.oszero.deliver.server.pretreatment.link.LinkContext;
import com.oszero.deliver.server.pretreatment.link.MessageLink;
import org.springframework.stereotype.Service;

/**
 * 企微通用参数校验
 *
 * @author oszero
 * @version 1.0.0
 */
@Service
public class WeChatCommonParamCheck implements MessageLink<SendTaskDto> {
    @Override
    public void process(LinkContext<SendTaskDto> context) {

    }
}
