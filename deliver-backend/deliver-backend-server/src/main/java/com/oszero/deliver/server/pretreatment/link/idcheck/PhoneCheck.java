package com.oszero.deliver.server.pretreatment.link.idcheck;

import cn.hutool.core.lang.Validator;
import com.oszero.deliver.server.exception.LinkProcessException;
import com.oszero.deliver.server.util.channel.DingUtils;
import com.oszero.deliver.server.util.channel.FeiShuUtils;
import com.oszero.deliver.server.util.channel.WeChatUtils;
import com.oszero.deliver.server.model.dto.SendTaskDto;
import com.oszero.deliver.server.pretreatment.constant.PretreatmentCodeConstant;
import com.oszero.deliver.server.pretreatment.link.BusinessLink;
import com.oszero.deliver.server.pretreatment.link.LinkContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PhoneCheck implements BusinessLink<SendTaskDto> {

    private final DingUtils dingUtils;
    private final WeChatUtils weChatUtils;
    private final FeiShuUtils feiShuUtils;

    private final Map<String, Phone2UserId> strategyMap;

    @Override
    public void process(LinkContext<SendTaskDto> context) {
        SendTaskDto sendTaskDto = context.getProcessModel();
        List<String> users = sendTaskDto.getUsers();
        users.forEach(phone -> {
            if (!Validator.isMobile(phone)) {
                throw new LinkProcessException("[PhoneCheck#process]错误：消息接收者中有非[电话号码]用户！");
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
