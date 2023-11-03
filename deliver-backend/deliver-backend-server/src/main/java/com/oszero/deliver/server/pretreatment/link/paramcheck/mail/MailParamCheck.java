package com.oszero.deliver.server.pretreatment.link.paramcheck.mail;

import cn.hutool.json.JSONUtil;
import com.oszero.deliver.server.exception.LinkProcessException;
import com.oszero.deliver.server.message.param.mail.MailParam;
import com.oszero.deliver.server.model.dto.SendTaskDto;
import com.oszero.deliver.server.pretreatment.link.BusinessLink;
import com.oszero.deliver.server.pretreatment.link.LinkContext;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 邮件消息参数校验
 *
 * @author oszero
 * @version 1.0.0
 */
@Service
public class MailParamCheck implements BusinessLink<SendTaskDto> {

    @Override
    public void process(LinkContext<SendTaskDto> context) {
        try {
            SendTaskDto sendTaskDto = context.getProcessModel();
            Map<String, Object> paramMap = sendTaskDto.getParamMap();
            String json = JSONUtil.toJsonStr(paramMap);
            JSONUtil.toBean(json, MailParam.class);
            sendTaskDto.setParamJson(json);
        } catch (Exception exception) {
            throw new LinkProcessException("邮件消息参数校验失败！！！");
        }
    }
}
