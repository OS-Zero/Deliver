package com.oszero.deliver.server.pretreatment.action.send;

import com.oszero.deliver.server.enums.ChannelTypeEnum;
import com.oszero.deliver.server.message.producer.Producer;
import com.oszero.deliver.server.model.dto.SendTaskDto;
import com.oszero.deliver.server.pretreatment.pipeline.BusinessProcess;
import com.oszero.deliver.server.pretreatment.pipeline.ProcessContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CallSendAction extends CommonSendAction implements BusinessProcess<SendTaskDto> {

    private Producer producer;

    @Override
    boolean send(SendTaskDto sendTaskDto) {
        ChannelTypeEnum channelTypeEnum = ChannelTypeEnum.getInstanceByCode(sendTaskDto.getChannelType());
        producer.sendMessage(channelTypeEnum, sendTaskDto);
        return false;
    }

    /**
     * 真正处理逻辑
     *
     * @param context
     */
    @Override
    public void process(ProcessContext<SendTaskDto> context) {
        sendToMq(context);
    }
}
