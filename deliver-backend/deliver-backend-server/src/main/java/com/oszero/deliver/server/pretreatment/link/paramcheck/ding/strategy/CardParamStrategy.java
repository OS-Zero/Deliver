package com.oszero.deliver.server.pretreatment.link.paramcheck.ding.strategy;

import com.oszero.deliver.server.model.dto.SendTaskDto;
import com.oszero.deliver.server.pretreatment.link.paramcheck.ParamStrategy;
import org.springframework.stereotype.Component;


/**
 * 钉钉卡片消息参数校验策略
 *
 * @author oszero
 * @version 1.0.0
 */
@Component(ParamStrategy.DING_STRATEGY_BEAN_PRE_NAME + "4-7")
public class CardParamStrategy implements ParamStrategy {
    @Override
    public void paramCheck(SendTaskDto sendTaskDto) throws Exception {

    }
}
