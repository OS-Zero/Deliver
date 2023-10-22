package com.oszero.deliver.server.pretreatment.link.paramcheck.feishu.strategy;

import com.oszero.deliver.server.model.dto.SendTaskDto;
import com.oszero.deliver.server.pretreatment.link.paramcheck.ParamStrategy;
import org.springframework.stereotype.Component;

@Component(ParamStrategy.FEI_SHU_STRATEGY_BEAN_PRE_NAME + "6-6")
public class AudioParamStrategy implements ParamStrategy {
    @Override
    public void paramCheck(SendTaskDto sendTaskDto) {
    }
}
