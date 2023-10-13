package com.oszero.deliver.server.pretreatment.action.paramCheck;

import cn.dianhun.om.message.constant.DingParamConstant;
import cn.dianhun.om.message.exception.PipelineProcessException;
import cn.dianhun.om.message.model.dto.SendTaskDto;
import cn.dianhun.om.message.pipeline.BusinessProcess;
import cn.dianhun.om.message.pipeline.ProcessContext;
import cn.dianhun.om.message.util.CommonUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author zbzbzzz
 * @email zbzbzzz@dianhun.cn
 * @date 2023/4/26 15:16
 */

@Service
public class DingOaPrarmCheckAction implements BusinessProcess<SendTaskDto> {
    @Override
    public void process(ProcessContext<SendTaskDto> context) {
        Map<String, String> paramMap = context.getProcessModel().getParamMap();
        CommonUtil.paramCheckAndThrow(paramMap, DingParamConstant.OA_URL);
        CommonUtil.paramCheckAndDefault(paramMap,DingParamConstant.OA_BODY,DingParamConstant.OA_BODY_DEFAULT);
        CommonUtil.paramCheckAndThrow(paramMap, DingParamConstant.OA_HEAD);
        JSONObject jsonObject = JSONUtil.parseObj(paramMap.get(DingParamConstant.OA_HEAD));
        if (!(jsonObject.containsKey("bgcolor") && jsonObject.containsKey("text"))) {
            throw new PipelineProcessException("缺少参数[head.bgcolor]或者[head.text]");
        }
    }
}
