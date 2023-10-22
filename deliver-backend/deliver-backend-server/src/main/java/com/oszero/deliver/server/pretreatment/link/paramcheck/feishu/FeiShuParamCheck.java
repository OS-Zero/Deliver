package com.oszero.deliver.server.pretreatment.link.paramcheck.feishu;

import com.oszero.deliver.server.model.dto.SendTaskDto;
import com.oszero.deliver.server.pretreatment.link.BusinessLink;
import com.oszero.deliver.server.pretreatment.link.LinkContext;
import com.oszero.deliver.server.pretreatment.link.paramcheck.ParamStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class FeiShuParamCheck implements BusinessLink<SendTaskDto> {

    private final Map<String, ParamStrategy> feiShuParamStrategyMap;

    @Override
    public void process(LinkContext<SendTaskDto> context) {
        SendTaskDto sendTaskDto = context.getProcessModel();
        // 飞书 userId 存在请求参数里
        sendTaskDto.getParamMap().put("user_ids", sendTaskDto.getUsers());
        String strategyBeanName = ParamStrategy.FEI_SHU_STRATEGY_BEAN_PRE_NAME + sendTaskDto.getMessageType();
        ParamStrategy paramStrategy = feiShuParamStrategyMap.get(strategyBeanName);
        paramStrategy.paramCheck(sendTaskDto);
    }
}
