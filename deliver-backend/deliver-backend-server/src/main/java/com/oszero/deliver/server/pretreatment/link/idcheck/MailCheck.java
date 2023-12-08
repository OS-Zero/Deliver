package com.oszero.deliver.server.pretreatment.link.idcheck;

import cn.hutool.core.lang.Validator;
import com.oszero.deliver.server.exception.MessageException;
import com.oszero.deliver.server.model.dto.SendTaskDto;
import com.oszero.deliver.server.pretreatment.link.BusinessLink;
import com.oszero.deliver.server.pretreatment.link.LinkContext;
import com.oszero.deliver.server.util.MessageLinkTraceUtils;
import org.springframework.stereotype.Service;

/**
 * 邮箱账号检查
 *
 * @author oszero
 * @version 1.0.0
 */
@Service
public class MailCheck implements BusinessLink<SendTaskDto> {

    @Override
    public void process(LinkContext<SendTaskDto> context) {
        SendTaskDto sendTaskDto = context.getProcessModel();
        for (String email : sendTaskDto.getUsers()) {
            if (!Validator.isEmail(email)) {
                throw new MessageException(sendTaskDto, "[MailCheck#process]错误：消息接收者中有非[邮箱地址]用户");
            }
        }
        MessageLinkTraceUtils.recordMessageLifecycleInfoLog(sendTaskDto, "完成邮箱检查");
    }
}
