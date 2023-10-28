package com.oszero.deliver.server.pretreatment.link.paramcheck.ding.strategy;

import cn.hutool.json.JSONUtil;
import com.oszero.deliver.server.message.param.ding.DingFileparam;
import com.oszero.deliver.server.message.param.ding.DingImageParam;
import com.oszero.deliver.server.model.dto.SendTaskDto;
import com.oszero.deliver.server.pretreatment.link.paramcheck.ParamStrategy;

import java.util.Map;

/**
 * 钉钉图片消息参数校验策略
 *
 * @author oszero
 * @version 1.0.0
 */
public class ImageParamStrategy implements ParamStrategy {
    @Override
    public void paramCheck(SendTaskDto sendTaskDto) throws Exception {
        Map<String, Object> paramMap = sendTaskDto.getParamMap();
        String json = JSONUtil.toJsonStr(paramMap);
        JSONUtil.toBean(json, DingImageParam.class);
        sendTaskDto.setParamJson(json);
    }
}
