package com.oszero.deliver.server.pretreatment.link.pushrangecheck;

import com.oszero.deliver.server.constant.TraceIdConstant;
import com.oszero.deliver.server.enums.ChannelTypeEnum;
import com.oszero.deliver.server.enums.PushRangeEnum;
import com.oszero.deliver.server.enums.UsersTypeEnum;
import com.oszero.deliver.server.exception.MessageException;
import com.oszero.deliver.server.log.MessageLinkTraceLogger;
import com.oszero.deliver.server.model.dto.SendTaskDto;
import com.oszero.deliver.server.pretreatment.link.BusinessLink;
import com.oszero.deliver.server.pretreatment.link.LinkContext;
import com.oszero.deliver.server.util.IpUtils;
import com.oszero.deliver.server.util.MDCUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 推送范围检测
 *
 * @author oszero
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class PushRangeCheck implements BusinessLink<SendTaskDto> {

    private final MessageLinkTraceLogger messageLinkTraceLogger;

    /**
     * 真正处理逻辑
     *
     * @param context 处理上下文
     */
    @Override
    public void process(LinkContext<SendTaskDto> context) {
        SendTaskDto sendTaskDto = context.getProcessModel();
        Integer pushRange = sendTaskDto.getPushRange();
        Integer usersType = sendTaskDto.getUsersType();
        Integer channelType = sendTaskDto.getChannelType();
        if (PushRangeEnum.ALL.getCode().equals(pushRange) || PushRangeEnum.INNER.getCode().equals(pushRange)) {
            messageLinkTraceLogger.info("消息链路 ID: {}, 模板 ID: {}, 应用 ID: {}, 接收人列表: {}, 是否重试消息: {}, 重试次数剩余: {}, 请求 IP: {}, 处理信息: {}"
                    , MDCUtils.get(TraceIdConstant.TRACE_ID)
                    ,sendTaskDto.getTemplateId()
                    ,sendTaskDto.getAppId()
                    ,sendTaskDto.getUsers()
                    ,sendTaskDto.getRetried()
                    ,sendTaskDto.getRetry()
                    , IpUtils.getClientIp()
                    ,"推送范围检测成功");
            return;
        }
        if (
                !(UsersTypeEnum.MAIL.getCode().equals(usersType) ||
                        UsersTypeEnum.PHONE.getCode().equals(usersType) && ChannelTypeEnum.CALL.getCode().equals(channelType))
        ) {
            throw new MessageException("推送范围错误！！！");
        }
    }
}
