package com.oszero.deliver.server.pretreatment.action.idcheck;

import com.oszero.deliver.server.message.util.FeiShuUtils;
import com.oszero.deliver.server.model.dto.SendTaskDto;
import com.oszero.deliver.server.pretreatment.pipeline.BusinessProcess;
import com.oszero.deliver.server.pretreatment.pipeline.ProcessContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeiShuUserIdCheckAction implements BusinessProcess<SendTaskDto> {

    private final FeiShuUtils feiShuUtils;

    @Override
    public void process(ProcessContext<SendTaskDto> context) {
        SendTaskDto sendTaskDto = context.getProcessModel();
        sendTaskDto.getUsers().stream().forEach(userId -> {

        });
    }
}
