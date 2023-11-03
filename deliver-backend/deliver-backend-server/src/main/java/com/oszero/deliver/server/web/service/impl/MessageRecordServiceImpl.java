package com.oszero.deliver.server.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oszero.deliver.server.enums.StatusEnum;
import com.oszero.deliver.server.model.dto.SendTaskDto;
import com.oszero.deliver.server.model.entity.MessageRecord;
import com.oszero.deliver.server.web.service.MessageRecordService;
import com.oszero.deliver.server.web.mapper.MessageRecordMapper;
import org.springframework.stereotype.Service;

/**
 * 针对表【message_record(消息记录)】的数据库操作Service实现
 *
 * @author oszero
 * @version 1.0.0
 */
@Service
public class MessageRecordServiceImpl extends ServiceImpl<MessageRecordMapper, MessageRecord>
        implements MessageRecordService {

    @Override
    public void saveMessageRecord(SendTaskDto sendTaskDto, StatusEnum messageStatus, String user) {

        MessageRecord messageRecord = MessageRecord.builder()
                .traceId(sendTaskDto.getTraceId())
                .templateId(sendTaskDto.getTemplateId())
                .appId(sendTaskDto.getAppId())
                .channelType(sendTaskDto.getChannelType())
                .messageType(sendTaskDto.getMessageType())
                .userType(sendTaskDto.getUsersType())
                .pushUser(user)
                .retried(sendTaskDto.getRetried())
                .messageStatus(messageStatus.getStatus())
                .build();
        this.save(messageRecord);
    }
}




