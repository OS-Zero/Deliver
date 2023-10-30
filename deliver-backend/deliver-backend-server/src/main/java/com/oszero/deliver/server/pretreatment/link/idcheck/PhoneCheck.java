package com.oszero.deliver.server.pretreatment.link.idcheck;

import cn.hutool.core.lang.Validator;
import cn.hutool.json.JSONUtil;
import com.oszero.deliver.server.exception.LinkProcessException;
import com.oszero.deliver.server.model.app.FeiShuApp;
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

/**
 * 手机号检查
 *
 * @author oszero
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class PhoneCheck implements BusinessLink<SendTaskDto> {

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
        // 获取 appConfig
        String appConfigJson = sendTaskDto.getAppConfigJson();
        // 策略模式实现 phone 转换平台 userId
        Phone2UserId phone2UserId = strategyMap.get(context.getCode());
        sendTaskDto.setUsers(phone2UserId.convert(appConfigJson, users));
    }

    /**
     * 手机号转 userId 接口
     */
    public interface Phone2UserId {
        List<String> convert(String appConfigJson, List<String> phones);
    }

    /**
     * 电话策略
     */
    @Component(PretreatmentCodeConstant.PHONE_CALL)
    public static class PhoneStrategy implements Phone2UserId {

        @Override
        public List<String> convert(String appConfigJson, List<String> phones) {
            return phones;
        }
    }

    /**
     * 短信策略
     */
    @Component(PretreatmentCodeConstant.PHONE_SMS)
    public static class SmsStrategy implements Phone2UserId {

        @Override
        public List<String> convert(String appConfigJson, List<String> phones) {
            return phones;
        }
    }

    /**
     * 钉钉
     */
    @Component(PretreatmentCodeConstant.PHONE_DING)
    @RequiredArgsConstructor
    public static class DingStrategy implements Phone2UserId {
        private final DingUtils dingUtils;

        @Override
        public List<String> convert(String appConfigJson, List<String> phones) {
            return null;
        }
    }

    /**
     * 企业微信
     */
    @Component(PretreatmentCodeConstant.PHONE_WECHAT)
    @RequiredArgsConstructor
    public static class WeChatStrategy implements Phone2UserId {
        private final WeChatUtils weChatUtils;

        @Override
        public List<String> convert(String appConfigJson, List<String> phones) {
            return null;
        }
    }

    /**
     * 飞书
     */
    @Component(PretreatmentCodeConstant.PHONE_FEI_SHU)
    @RequiredArgsConstructor
    public static class FeiShuStrategy implements Phone2UserId {
        private final FeiShuUtils feiShuUtils;

        @Override
        public List<String> convert(String appConfigJson, List<String> phones) {
            String tenantAccessToken = feiShuUtils.getTenantAccessToken(JSONUtil.toBean(appConfigJson, FeiShuApp.class));
            return feiShuUtils.getUserIdsByPhones(tenantAccessToken, phones);
        }
    }
}
