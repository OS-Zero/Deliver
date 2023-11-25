package com.oszero.deliver.server.pretreatment.link.idcheck;

import cn.hutool.core.lang.Validator;
import com.oszero.deliver.server.exception.LinkProcessException;
import com.oszero.deliver.server.model.dto.SendTaskDto;
import com.oszero.deliver.server.pretreatment.link.BusinessLink;
import com.oszero.deliver.server.pretreatment.link.LinkContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 手机号检查
 *
 * @author oszero
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class PhoneCheck implements BusinessLink<SendTaskDto> {

    @Override
    public void process(LinkContext<SendTaskDto> context) {
        SendTaskDto sendTaskDto = context.getProcessModel();
        List<String> users = sendTaskDto.getUsers();
        users.forEach(phone -> {
            if (!Validator.isMobile(phone)) {
                throw new LinkProcessException("[PhoneCheck#process]错误：消息接收者中有非[电话号码]用户！");
            }
        });
    }
}
