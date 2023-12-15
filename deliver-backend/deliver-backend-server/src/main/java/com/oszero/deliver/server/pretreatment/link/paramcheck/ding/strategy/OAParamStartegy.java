package com.oszero.deliver.server.pretreatment.link.paramcheck.ding.strategy;

import cn.hutool.json.JSONUtil;
import com.oszero.deliver.server.enums.MessageTypeEnum;
import com.oszero.deliver.server.model.messageparam.ding.DingOAParam;
import com.oszero.deliver.server.model.dto.common.SendTaskDto;
import com.oszero.deliver.server.pretreatment.link.paramcheck.ParamStrategy;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 钉钉OA消息参数校验策略
 *
 * @author oszero
 * @version 1.0.0
 */
@Component(ParamStrategy.DING_STRATEGY_BEAN_PRE_NAME + "4-5")
public class OAParamStartegy implements ParamStrategy {

    @Override
    public void paramCheck(SendTaskDto sendTaskDto) throws Exception {
        Map<String, Object> paramMap = sendTaskDto.getParamMap();
        String msg = JSONUtil.toJsonStr(paramMap.get("msg"));
        DingOAParam.OAMessageDTO oAMessageDTO = JSONUtil.toBean(msg, DingOAParam.OAMessageDTO.class);
        oAMessageDTO.setMsgtype(MessageTypeEnum.DING_OA.getMsgType());
        paramMap.put("msg", oAMessageDTO);

        // TODO:参数校验
    }
}
