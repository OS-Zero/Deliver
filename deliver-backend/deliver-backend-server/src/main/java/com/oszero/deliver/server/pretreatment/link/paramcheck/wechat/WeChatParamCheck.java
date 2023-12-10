package com.oszero.deliver.server.pretreatment.link.paramcheck.wechat;

import cn.hutool.json.JSONUtil;
import com.oszero.deliver.server.exception.MessageException;
import com.oszero.deliver.server.model.app.WeChatApp;
import com.oszero.deliver.server.model.dto.SendTaskDto;
import com.oszero.deliver.server.pretreatment.common.MessageLink;
import com.oszero.deliver.server.pretreatment.common.LinkContext;
import com.oszero.deliver.server.pretreatment.link.paramcheck.ParamStrategy;
import com.oszero.deliver.server.util.MessageLinkTraceUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 企业微信消息参数校验
 *
 * @author oszero
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class WeChatParamCheck implements MessageLink<SendTaskDto> {

    private final Map<String, ParamStrategy> wechatParamStrategyMap;

    @Override
    public void process(LinkContext<SendTaskDto> context) {
        SendTaskDto sendTaskDto = context.getProcessModel();

        String appConfig = sendTaskDto.getAppConfig();
        WeChatApp weChatApp = JSONUtil.toBean(appConfig, WeChatApp.class);

        Map<String, Object> paramMap = sendTaskDto.getParamMap();
        String pushSubject = paramMap.get("pushSubject").toString();
        String wechatUserIdType = paramMap.get("wechatUserIdType").toString();

        List<String> users = sendTaskDto.getUsers();
        if ("app".equals(pushSubject)) {
            switch (wechatUserIdType) {
                case "touser":
                case "toparty":
                case "totag": {
                    paramMap.put("agentid", weChatApp.getAgentid());
                    paramMap.put(wechatUserIdType, String.join("|", users));
                    break;
                }
                case "to_parent_userid":
                case "to_student_userid":
                case "to_party": {
                    paramMap.put("agentid", weChatApp.getAgentid());
                    paramMap.put(wechatUserIdType, users);
                    break;
                }
                case "toall": {
                    paramMap.put("agentid", weChatApp.getAgentid());
                    paramMap.put(wechatUserIdType, users.get(0));
                    break;
                }
                case "chatid": {
                    paramMap.put(wechatUserIdType, users.get(0));
                    break;
                }
                default: {
                }
            }
        }

        try {
            String strategyBeanName = ParamStrategy.WECHAT_STRATEGY_BEAN_PRE_NAME + sendTaskDto.getMessageType();
            ParamStrategy paramStrategy = wechatParamStrategyMap.get(strategyBeanName);
            paramStrategy.paramCheck(sendTaskDto);
        } catch (Exception exception) {
            throw new MessageException(sendTaskDto, "企微消息参数校验失败，" + exception.getMessage());
        }

        MessageLinkTraceUtils.recordMessageLifecycleInfoLog(sendTaskDto, "完成企微消息参数校验");
    }
}
