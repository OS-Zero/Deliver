package com.oszero.deliver.server.pretreatment.link.convert;

import cn.hutool.json.JSONUtil;
import com.oszero.deliver.server.client.DingClient;
import com.oszero.deliver.server.client.FeiShuClient;
import com.oszero.deliver.server.client.WeChatClient;
import com.oszero.deliver.server.model.app.DingApp;
import com.oszero.deliver.server.model.app.FeiShuApp;
import com.oszero.deliver.server.model.app.WeChatApp;
import com.oszero.deliver.server.model.dto.SendTaskDto;
import com.oszero.deliver.server.pretreatment.constant.PretreatmentCodeConstant;
import com.oszero.deliver.server.pretreatment.common.MessageLink;
import com.oszero.deliver.server.pretreatment.common.LinkContext;
import com.oszero.deliver.server.util.MessageLinkTraceUtils;
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
public class Phone2UserIdConvert implements MessageLink<SendTaskDto> {

    private final Map<String, Phone2UserId> strategyMap;

    @Override
    public void process(LinkContext<SendTaskDto> context) {
        SendTaskDto sendTaskDto = context.getProcessModel();
        List<String> users = sendTaskDto.getUsers();
        // 获取 appConfig
        String appConfigJson = sendTaskDto.getAppConfig();
        // 策略模式实现 phone 转换平台 userId
        Phone2UserId phone2UserId = strategyMap.get(context.getCode());
        sendTaskDto.setUsers(phone2UserId.convert(appConfigJson, users, sendTaskDto));

        MessageLinkTraceUtils.recordMessageLifecycleInfoLog(sendTaskDto, "完成手机号转换平台 ID");
    }

    /**
     * 手机号转 userId 接口
     */
    public interface Phone2UserId {
        List<String> convert(String appConfigJson, List<String> phones, SendTaskDto sendTaskDto);
    }

    /**
     * 钉钉
     */
    @Component(PretreatmentCodeConstant.PHONE_DING)
    @RequiredArgsConstructor
    public static class DingStrategy implements Phone2UserId {
        private final DingClient dingClient;

        @Override
        public List<String> convert(String appConfigJson, List<String> phones, SendTaskDto sendTaskDto) {
            String accessToken = dingClient.getAccessToken(JSONUtil.toBean(appConfigJson, DingApp.class), sendTaskDto);
            return phones.stream().map(phone -> dingClient.getUserIdByPhone(accessToken, phone, sendTaskDto)).collect(Collectors.toList());
        }
    }

    /**
     * 企业微信
     */
    @Component(PretreatmentCodeConstant.PHONE_WECHAT)
    @RequiredArgsConstructor
    public static class WeChatStrategy implements Phone2UserId {
        private final WeChatClient weChatClient;

        @Override
        public List<String> convert(String appConfigJson, List<String> phones, SendTaskDto sendTaskDto) {
            String accessToken = weChatClient.getAccessToken(JSONUtil.toBean(appConfigJson, WeChatApp.class), sendTaskDto);
            return weChatClient.getUserIdByPhone(accessToken, phones, sendTaskDto);
        }
    }

    /**
     * 飞书
     */
    @Component(PretreatmentCodeConstant.PHONE_FEI_SHU)
    @RequiredArgsConstructor
    public static class FeiShuStrategy implements Phone2UserId {
        private final FeiShuClient feiShuClient;

        @Override
        public List<String> convert(String appConfigJson, List<String> phones, SendTaskDto sendTaskDto) {
            String tenantAccessToken = feiShuClient.getTenantAccessToken(JSONUtil.toBean(appConfigJson, FeiShuApp.class), sendTaskDto);
            return feiShuClient.getUserIdsByPhones(tenantAccessToken, phones, sendTaskDto);
        }
    }
}
