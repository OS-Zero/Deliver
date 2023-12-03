package com.oszero.deliver.server.pretreatment.link.paramcheck.wechat;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.oszero.deliver.server.exception.MessageException;
import com.oszero.deliver.server.model.app.WeChatApp;
import com.oszero.deliver.server.model.dto.SendTaskDto;
import com.oszero.deliver.server.pretreatment.link.BusinessLink;
import com.oszero.deliver.server.pretreatment.link.LinkContext;
import com.oszero.deliver.server.pretreatment.link.paramcheck.ParamStrategy;
import com.oszero.deliver.server.util.AesUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 企业微信消息参数校验
 *
 * @author oszero
 * @version 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WeChatParamCheck implements BusinessLink<SendTaskDto> {

    private final Map<String, ParamStrategy> wechatParamStrategyMap;
    private final AesUtils aesUtils;

    @Override
    public void process(LinkContext<SendTaskDto> context) {
        SendTaskDto sendTaskDto = context.getProcessModel();

        String appConfig = aesUtils.decrypt(sendTaskDto.getAppConfig());
        WeChatApp weChatApp = JSONUtil.toBean(appConfig, WeChatApp.class);

        Map<String, Object> paramMap = sendTaskDto.getParamMap();
        String pushSubject = paramMap.getOrDefault("pushSubject", "").toString();
        String wechatUserIdType = paramMap.getOrDefault("wechatUserIdType", "").toString();

        if (StrUtil.isBlank(pushSubject) || StrUtil.isBlank(wechatUserIdType)) {
            throw new MessageException("请传递参数：pushSubject 和 wechatUserIdType！！！");
        }

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
                    break;
                }
                case "chatid": {
                    paramMap.put(wechatUserIdType, users.get(0));
                    break;
                }
                default: {
                    throw new MessageException("输入了非法的 wechatUserIdType，应该为 touser 或者 toparty 或者 totag 或者 to_parent_userid 或者 to_student_userid 或者 to_party 或者 toall 或者 chatid！！！");
                }
            }
        } else if ("robot".equals(pushSubject)) {
            if (!"webhook".equals(wechatUserIdType)) {
                throw new MessageException("输入了非法的 wechatUserIdType，应该为 webhook！！！");
            }
        } else {
            throw new MessageException("输入了非法的 pushSubject，应该为 app 或者 robot！！！");
        }

        String strategyBeanName = ParamStrategy.WECHAT_STRATEGY_BEAN_PRE_NAME + sendTaskDto.getMessageType();
        ParamStrategy paramStrategy = wechatParamStrategyMap.get(strategyBeanName);
        try {
            paramStrategy.paramCheck(sendTaskDto);
        } catch (Exception exception) {
            log.error("[WeChatParamCheck#process]异常：{}", exception.toString());
            throw new MessageException("企微消息参数校验失败，" + exception.getMessage() + "！！！");
        }
    }
}
