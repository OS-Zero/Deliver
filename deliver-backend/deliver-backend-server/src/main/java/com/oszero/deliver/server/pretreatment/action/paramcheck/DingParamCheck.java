package com.oszero.deliver.server.pretreatment.action.paramcheck;

import cn.hutool.core.lang.hash.Hash;
import com.oszero.deliver.server.model.dto.SendTaskDto;
import com.oszero.deliver.server.pretreatment.pipeline.BusinessProcess;
import com.oszero.deliver.server.pretreatment.pipeline.ProcessContext;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class DingParamCheck implements BusinessProcess<SendTaskDto> {

    @Override
    public void process(ProcessContext<SendTaskDto> context) {
        SendTaskDto sendTaskDto = context.getProcessModel();
        String messageType = sendTaskDto.getMessageType();
    }
}
