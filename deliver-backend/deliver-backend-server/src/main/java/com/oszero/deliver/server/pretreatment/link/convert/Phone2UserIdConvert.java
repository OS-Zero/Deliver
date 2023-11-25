package com.oszero.deliver.server.pretreatment.link.convert;

import cn.hutool.json.JSONUtil;
import com.oszero.deliver.server.model.app.DingApp;
import com.oszero.deliver.server.model.app.FeiShuApp;
import com.oszero.deliver.server.model.dto.SendTaskDto;
import com.oszero.deliver.server.pretreatment.constant.PretreatmentCodeConstant;
import com.oszero.deliver.server.pretreatment.link.BusinessLink;
import com.oszero.deliver.server.pretreatment.link.LinkContext;
import com.oszero.deliver.server.util.channel.DingUtils;
import com.oszero.deliver.server.util.channel.FeiShuUtils;
import com.oszero.deliver.server.util.channel.WeChatUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 手机号转换平台 ID
 *
 * @author oszero
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class Phone2UserIdConvert implements BusinessLink<SendTaskDto> {

    private final Map<String, Phone2UserId> strategyMap;

    @Override
    public void process(LinkContext<SendTaskDto> context) {
        SendTaskDto sendTaskDto = context.getProcessModel();
        List<String> users = sendTaskDto.getUsers();
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
     * 钉钉
     */
    @Component(PretreatmentCodeConstant.PHONE_DING)
    @RequiredArgsConstructor
    public static class DingStrategy implements Phone2UserId {
        private final DingUtils dingUtils;

        @Override
        public List<String> convert(String appConfigJson, List<String> phones) {
            String accessToken = dingUtils.getAccessToken(JSONUtil.toBean(appConfigJson, DingApp.class));
            return phones.stream().map(phone -> dingUtils.getUserIdByPhone(accessToken, phone)).collect(Collectors.toList());
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
