package com.oszero.deliver.server.pretreatment.action.idcheck;

import cn.hutool.core.lang.Validator;
import com.oszero.deliver.server.exception.PipelineProcessException;
import com.oszero.deliver.server.model.dto.SendTaskDto;
import com.oszero.deliver.server.pretreatment.pipeline.BusinessProcess;
import com.oszero.deliver.server.pretreatment.pipeline.ProcessContext;
import org.springframework.stereotype.Service;

@Service
public class PhoneCheckAction implements BusinessProcess<SendTaskDto> {
    @Override
    public void process(ProcessContext<?> context) {
        for (String phone : context.getProcessModel().getUsers()) {
            if (!Validator.isMobile(phone)) {
                throw new PipelineProcessException("消息接收者中有非[电话号码]用户！");
            }
        }
    }
}
