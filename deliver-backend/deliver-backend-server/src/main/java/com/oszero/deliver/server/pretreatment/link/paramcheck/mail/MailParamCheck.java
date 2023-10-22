package com.oszero.deliver.server.pretreatment.link.paramcheck.mail;

import cn.hutool.json.JSONUtil;
import com.oszero.deliver.server.exception.LinkProcessException;
import com.oszero.deliver.server.message.param.mail.MailParam;
import com.oszero.deliver.server.model.dto.SendTaskDto;
import com.oszero.deliver.server.pretreatment.link.BusinessLink;
import com.oszero.deliver.server.pretreatment.link.LinkContext;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MailParamCheck implements BusinessLink<SendTaskDto> {

    @Override
    public void process(LinkContext<SendTaskDto> context) {
        try {
            SendTaskDto sendTaskDto = context.getProcessModel();
            Map<String, Object> paramMap = sendTaskDto.getParamMap();
            String json = JSONUtil.toJsonStr(paramMap);
            MailParam mailParam = JSONUtil.toBean(json, MailParam.class);
            sendTaskDto.setParam(mailParam);
        } catch (Exception exception) {
            throw new LinkProcessException("飞书 text 类型消息校验失败");
        }
    }
}
