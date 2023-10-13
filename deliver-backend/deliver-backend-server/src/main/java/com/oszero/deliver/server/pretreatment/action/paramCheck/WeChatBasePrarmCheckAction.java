package com.oszero.deliver.server.pretreatment.action.paramCheck;

import cn.dianhun.om.message.constant.WeChatParamConstant;
import cn.dianhun.om.message.exception.PipelineProcessException;
import cn.dianhun.om.message.model.dto.SendTaskDto;
import cn.dianhun.om.message.pipeline.BusinessProcess;
import cn.dianhun.om.message.pipeline.ProcessContext;
import cn.dianhun.om.message.util.CommonUtil;
import cn.hutool.json.JSONUtil;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author zbzbzzz
 * @email zbzbzzz@dianhun.cn
 * @date 2023/4/20 10:05
 */

@Service
public class WeChatBasePrarmCheckAction implements BusinessProcess<SendTaskDto> {
    @Override
    public void process(ProcessContext<SendTaskDto> context) {
        // TODO 暂时不考虑 toParty 和 toTag
        Map<String, String> paramMap = context.getProcessModel().getParamMap();
        CommonUtil.paramCheckAndDefault(paramMap, WeChatParamConstant.TOPARTY,WeChatParamConstant.DEFAULT_TOPARTY);
        CommonUtil.paramCheckAndDefault(paramMap,WeChatParamConstant.TOTAG,WeChatParamConstant.DEFAULT_TOTAG);
        if (!JSONUtil.isTypeJSONArray(paramMap.get(WeChatParamConstant.TOPARTY)))
        {
            throw new PipelineProcessException("toParty列表不符合JsonArray定义！");
        }
        if (!JSONUtil.isTypeJSONArray(paramMap.get(WeChatParamConstant.TOTAG)))
        {
            throw new PipelineProcessException("toTag列表不符合JsonArray定义！");
        }
    }


}
