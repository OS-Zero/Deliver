package com.oszero.deliver.server.pretreatment.action.paramCheck;

import com.oszero.deliver.server.constant.DingParamConstant;
import com.oszero.deliver.server.model.dto.SendTaskDto;
import com.oszero.deliver.server.pretreatment.pipeline.BusinessProcess;
import com.oszero.deliver.server.pretreatment.pipeline.ProcessContext;
import com.oszero.deliver.server.util.CommonUtil;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class DingCardParamCheckAction  implements BusinessProcess<SendTaskDto> {
    @Override
    public void process(ProcessContext<SendTaskDto> context) {
        Map<String, String> paramMap = context.getProcessModel().getParamMap();
        CommonUtil.paramCheckAndThrow(paramMap, DingParamConstant.CARD_MD);
        CommonUtil.paramCheckAndThrow(paramMap, DingParamConstant.CARD_TITLE);
        CommonUtil.paramCheckAndThrow(paramMap, DingParamConstant.CARD_SINGLETITLE);
        CommonUtil.paramCheckAndThrow(paramMap, DingParamConstant.CARD_SINGLEURL);
        // TODO 还有另一种有按钮的卡片形式后面加

//         CommonUtil.isJsonArrayAndThrow(paramMap,DingParamConstant.CARD_BTNLIST);

    }
}
