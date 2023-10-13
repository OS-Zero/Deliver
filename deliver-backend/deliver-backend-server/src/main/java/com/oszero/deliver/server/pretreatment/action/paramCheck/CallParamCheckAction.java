package com.oszero.deliver.server.pretreatment.action.paramCheck;

import cn.dianhun.om.message.constant.CallParamConstant;
import cn.dianhun.om.message.exception.PipelineProcessException;
import cn.dianhun.om.message.model.dto.SendTaskDto;
import cn.dianhun.om.message.pipeline.BusinessProcess;
import cn.dianhun.om.message.pipeline.ProcessContext;
import cn.dianhun.om.message.util.CommonUtil;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author zbzbzzz
 * @email zbzbzzz@dianhun.cn
 * @date 2023/4/20 10:05
 */
@Service
public class CallParamCheckAction implements BusinessProcess<SendTaskDto> {
    @Override
    public void process(ProcessContext<SendTaskDto> context) {
        Map<String, String> paramMap = context.getProcessModel().getParamMap();
        CommonUtil.paramCheckAndThrow(paramMap, CallParamConstant.SUBJECT);
        CommonUtil.paramCheckAndThrow(paramMap, CallParamConstant.CONTENT);
        if (paramMap.get(CallParamConstant.SUBJECT).length()>5)
        {
            throw new PipelineProcessException("电话主题[title]参数过长");
        }
        paramMap.put(CallParamConstant.TYPE,CallParamConstant.TYPE_CALL);
    }
}
