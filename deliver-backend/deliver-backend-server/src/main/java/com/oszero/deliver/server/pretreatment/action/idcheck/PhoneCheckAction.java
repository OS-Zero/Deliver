package com.oszero.deliver.server.pretreatment.action.idcheck;

import cn.hutool.core.lang.Validator;
import com.oszero.deliver.server.exception.PipelineProcessException;
import com.oszero.deliver.server.message.util.DingUtils;
import com.oszero.deliver.server.message.util.FeiShuUtils;
import com.oszero.deliver.server.message.util.WeChatUtils;
import com.oszero.deliver.server.model.dto.SendTaskDto;
import com.oszero.deliver.server.pretreatment.constant.PretreatmentCodeConstant;
import com.oszero.deliver.server.pretreatment.pipeline.BusinessProcess;
import com.oszero.deliver.server.pretreatment.pipeline.ProcessContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PhoneCheckAction implements BusinessProcess<SendTaskDto> {

    private final DingUtils dingUtils;
    private final WeChatUtils weChatUtils;
    private final FeiShuUtils feiShuUtils;

    private final Map<String, Phone2UserId> strategyMap;

    @Override
    public void process(ProcessContext<SendTaskDto> context) {
        SendTaskDto sendTaskDto = context.getProcessModel();
        List<String> users = sendTaskDto.getUsers();
        users.forEach(phone -> {
            if (!Validator.isMobile(phone)) {
                throw new PipelineProcessException("[PhoneCheckAction#process]错误：消息接收者中有非[电话号码]用户！");
            }
        });
        // 策略模式实现 phone 转换平台 userId
        Phone2UserId phone2UserId = strategyMap.get(context.getCode());
        sendTaskDto.setUsers(users.stream().map(phone2UserId::convert).collect(Collectors.toList()));
    }

    private interface Phone2UserId {
        String convert(String phone);
    }

    @Component(PretreatmentCodeConstant.PHONE_CALL)
    public static class NoStrategy implements Phone2UserId {

        @Override
        public String convert(String phone) {
            return phone;
        }
    }

    @Component(PretreatmentCodeConstant.PHONE_DING)
    public static class DingStrategy implements Phone2UserId {

        @Override
        public String convert(String phone) {
            return null;
        }
    }

    @Component(PretreatmentCodeConstant.PHONE_WECHAT)
    public static class WeChatStrategy implements Phone2UserId {

        @Override
        public String convert(String phone) {
            return null;
        }
    }

    @Component(PretreatmentCodeConstant.PHONE_FEI_SHU)
    public static class FeiShuStrategy implements Phone2UserId {

        @Override
        public String convert(String phone) {
            return null;
        }
    }
}
