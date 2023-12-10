package com.oszero.deliver.server.pretreatment.link.paramcheck.feishu;

import com.oszero.deliver.server.exception.MessageException;
import com.oszero.deliver.server.model.dto.common.SendTaskDto;
import com.oszero.deliver.server.pretreatment.common.MessageLink;
import com.oszero.deliver.server.pretreatment.common.LinkContext;
import com.oszero.deliver.server.pretreatment.link.paramcheck.ParamStrategy;
import com.oszero.deliver.server.util.MessageLinkTraceUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 飞书消息参数校验
 *
 * @author oszero
 * @version 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FeiShuParamCheck implements MessageLink<SendTaskDto> {

    private final Map<String, ParamStrategy> feiShuParamStrategyMap;

    @Override
    public void process(LinkContext<SendTaskDto> context) {
        SendTaskDto sendTaskDto = context.getProcessModel();
        // 飞书 userId 存在请求参数里
        sendTaskDto.getParamMap().put("user_ids", sendTaskDto.getUsers());

        try {
            String strategyBeanName = ParamStrategy.FEI_SHU_STRATEGY_BEAN_PRE_NAME + sendTaskDto.getMessageType();
            ParamStrategy paramStrategy = feiShuParamStrategyMap.get(strategyBeanName);
            paramStrategy.paramCheck(sendTaskDto);
        } catch (Exception exception) {
            throw new MessageException(sendTaskDto, "飞书消息参数校验失败，" + exception.getMessage());
        }

        MessageLinkTraceUtils.recordMessageLifecycleInfoLog(sendTaskDto, "完成飞书参数校验");
    }
}
