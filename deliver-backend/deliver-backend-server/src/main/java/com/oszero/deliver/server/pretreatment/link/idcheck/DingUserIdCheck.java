package com.oszero.deliver.server.pretreatment.link.idcheck;

import com.oszero.deliver.server.util.channel.DingUtils;
import com.oszero.deliver.server.model.dto.SendTaskDto;
import com.oszero.deliver.server.pretreatment.link.BusinessLink;
import com.oszero.deliver.server.pretreatment.link.LinkContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DingUserIdCheck implements BusinessLink<SendTaskDto> {

    private final DingUtils dingUtils;

    @Override
    public void process(LinkContext<SendTaskDto> context) {
        SendTaskDto sendTaskDto = context.getProcessModel();
        sendTaskDto.getUsers().stream().forEach(userId -> {

        });
    }
}
