package com.oszero.deliver.server.pretreatment.link.pushrangecheck;

import com.oszero.deliver.server.enums.ChannelTypeEnum;
import com.oszero.deliver.server.enums.PushRangeEnum;
import com.oszero.deliver.server.enums.UsersTypeEnum;
import com.oszero.deliver.server.exception.LinkProcessException;
import com.oszero.deliver.server.model.dto.SendTaskDto;
import com.oszero.deliver.server.pretreatment.link.BusinessLink;
import com.oszero.deliver.server.pretreatment.link.LinkContext;
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
            return;
        }
        if (
                !(UsersTypeEnum.MAIL.getCode().equals(usersType) ||
                        UsersTypeEnum.PHONE.getCode().equals(usersType) && ChannelTypeEnum.CALL.getCode().equals(channelType))
        ) {
            throw new LinkProcessException("推送范围错误！！！");
        }
    }
}
