package com.oszero.deliver.server.pretreatment.action.send;

import com.oszero.deliver.server.model.dto.SendTaskDto;
import com.oszero.deliver.server.pretreatment.pipeline.BusinessProcess;
import com.oszero.deliver.server.pretreatment.pipeline.ProcessContext;
import org.springframework.stereotype.Service;

@Service
public class DingSendAction extends CommonSendAction implements BusinessProcess<SendTaskDto> {


    @Override
    boolean send(SendTaskDto sendTaskDto) {
        return false;
    }

    /**
     * 真正处理逻辑
     *
     * @param context
     */
    @Override
    public void process(ProcessContext<?> context) {
        sendToMq(context);
    }
}
