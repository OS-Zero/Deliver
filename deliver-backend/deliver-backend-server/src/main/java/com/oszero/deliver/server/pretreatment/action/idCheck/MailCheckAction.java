package com.oszero.deliver.server.pretreatment.action.idCheck;

import cn.hutool.core.lang.Validator;
import com.oszero.deliver.server.exception.PipelineProcessException;
import com.oszero.deliver.server.model.dto.SendTaskDto;
import com.oszero.deliver.server.pretreatment.pipeline.BusinessProcess;
import com.oszero.deliver.server.pretreatment.pipeline.ProcessContext;
import org.springframework.stereotype.Service;

@Service
public class MailCheckAction implements BusinessProcess<SendTaskDto> {
    @Override
    public void process(ProcessContext<SendTaskDto> context) {
        for (String email : context.getProcessModel().getUsers()) {
            if (!Validator.isEmail(email)) {
                throw new PipelineProcessException("消息接收者中有非[邮箱地址]用户！");
            }
        }
    }
}
