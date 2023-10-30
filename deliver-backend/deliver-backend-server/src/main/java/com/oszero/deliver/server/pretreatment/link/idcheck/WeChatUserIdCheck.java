package com.oszero.deliver.server.pretreatment.link.idcheck;

import com.oszero.deliver.server.util.channel.WeChatUtils;
import com.oszero.deliver.server.model.dto.SendTaskDto;
import com.oszero.deliver.server.pretreatment.link.BusinessLink;
import com.oszero.deliver.server.pretreatment.link.LinkContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 企业微信 userId 检查
 *
 * @author oszero
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class WeChatUserIdCheck implements BusinessLink<SendTaskDto> {

    private final WeChatUtils weChatUtils;

    @Override
    public void process(LinkContext<SendTaskDto> context) {
        SendTaskDto sendTaskDto = context.getProcessModel();
        sendTaskDto.getUsers().forEach(userId -> {

        });
    }
}
