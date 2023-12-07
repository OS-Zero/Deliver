package com.oszero.deliver.server.pretreatment.link.paramcheck.mail;

import cn.hutool.json.JSONUtil;
import com.oszero.deliver.server.exception.MessageException;
import com.oszero.deliver.server.message.param.mail.MailParam;
import com.oszero.deliver.server.model.dto.SendTaskDto;
import com.oszero.deliver.server.pretreatment.link.BusinessLink;
import com.oszero.deliver.server.pretreatment.link.LinkContext;
import com.oszero.deliver.server.util.MessageLinkTraceUtils;
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
        SendTaskDto sendTaskDto = context.getProcessModel();

        try {
            Map<String, Object> paramMap = sendTaskDto.getParamMap();
            String json = JSONUtil.toJsonStr(paramMap);
            JSONUtil.toBean(json, MailParam.class);
            sendTaskDto.setParamJson(json);
        } catch (Exception exception) {
            throw new MessageException(MessageLinkTraceUtils.formatMessageLifecycleErrorLogMsg(sendTaskDto, "邮件消息参数校验失败，" + exception.getMessage() + "！！！"));
        }

        MessageLinkTraceUtils.recordMessageLifecycleInfoLog(sendTaskDto, "完成邮件消息参数校验");
    }
}
