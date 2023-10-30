package com.oszero.deliver.server.pretreatment.link.paramcheck.feishu.strategy;

import cn.hutool.json.JSONUtil;
import com.oszero.deliver.server.enums.MessageTypeEnum;
import com.oszero.deliver.server.message.param.feishu.FeiShuShareUserParam;
import com.oszero.deliver.server.message.param.feishu.FeiShuTextParam;
import com.oszero.deliver.server.model.dto.SendTaskDto;
import com.oszero.deliver.server.pretreatment.link.paramcheck.ParamStrategy;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 飞书分享个人消息参数校验策略
 *
 * @author oszero
 * @version 1.0.0
 */
@Component(ParamStrategy.FEI_SHU_STRATEGY_BEAN_PRE_NAME + "6-5")
public class ShareUserParamStrategy implements ParamStrategy {

    @Override
    public void paramCheck(SendTaskDto sendTaskDto) throws Exception {
        Map<String, Object> paramMap = sendTaskDto.getParamMap();
        paramMap.put("msg_type", MessageTypeEnum.FEI_SHU_SHARE_USER.getMsgType());
        String json = JSONUtil.toJsonStr(paramMap);
        JSONUtil.toBean(json, FeiShuShareUserParam.class);
        sendTaskDto.setParamJson(json);
    }
}
