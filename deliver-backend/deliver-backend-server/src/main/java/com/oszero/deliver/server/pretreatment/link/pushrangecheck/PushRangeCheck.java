package com.oszero.deliver.server.pretreatment.link.pushrangecheck;

import com.oszero.deliver.server.enums.ChannelTypeEnum;
import com.oszero.deliver.server.enums.PushRangeEnum;
import com.oszero.deliver.server.enums.UsersTypeEnum;
import com.oszero.deliver.server.exception.MessageException;
import com.oszero.deliver.server.model.dto.SendTaskDto;
import com.oszero.deliver.server.pretreatment.link.BusinessLink;
import com.oszero.deliver.server.pretreatment.link.LinkContext;
import com.oszero.deliver.server.util.MessageLinkTraceUtils;
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
            // 企业直接为 true
            boolean company = UsersTypeEnum.COMPANY_ACCOUNT.getCode().equals(usersType);
            // 电话支持打电话、发短信、钉钉、企微、飞书
            boolean phone = UsersTypeEnum.PHONE.getCode().equals(usersType)
                    && (ChannelTypeEnum.CALL.getCode().equals(channelType)
                    || ChannelTypeEnum.SMS.getCode().equals(channelType)
                    || ChannelTypeEnum.DING.getCode().equals(channelType)
                    || ChannelTypeEnum.WECHAT.getCode().equals(channelType)
                    || ChannelTypeEnum.FEI_SHU.getCode().equals(channelType));
            // 邮箱支持发邮件
            boolean mail = UsersTypeEnum.MAIL.getCode().equals(usersType)
                    && ChannelTypeEnum.MAIL.getCode().equals(channelType);
            // 平台 ID 支持钉钉、企微、飞书
            boolean userId = UsersTypeEnum.PLATFORM_USER_ID.getCode().equals(usersType)
                    && (ChannelTypeEnum.DING.getCode().equals(channelType)
                    || ChannelTypeEnum.WECHAT.getCode().equals(channelType)
                    || ChannelTypeEnum.FEI_SHU.getCode().equals(channelType));
            // 任一一个失败则表示配置错误
            if (!(company || phone || mail || userId)) {
                throw new MessageException(MessageLinkTraceUtils.formatMessageLifecycleErrorLogMsg(sendTaskDto, "推送范围错误！！！"));
            }
        } else { // 企业外部只有电话与邮箱
            // 电话支持打电话、发短信
            boolean phone = UsersTypeEnum.PHONE.getCode().equals(usersType)
                    && (ChannelTypeEnum.CALL.getCode().equals(channelType)
                    || ChannelTypeEnum.SMS.getCode().equals(channelType));
            // 邮箱支持发邮件
            boolean mail = UsersTypeEnum.MAIL.getCode().equals(usersType) && ChannelTypeEnum.MAIL.getCode().equals(channelType);
            // 任一一个失败则表示配置错误
            if (!(mail || phone)) {
                throw new MessageException(MessageLinkTraceUtils.formatMessageLifecycleErrorLogMsg(sendTaskDto, "推送范围错误！！！"));
            }
        }

        MessageLinkTraceUtils.recordMessageLifecycleInfoLog(sendTaskDto, "完成推送范围检查");
    }
}
