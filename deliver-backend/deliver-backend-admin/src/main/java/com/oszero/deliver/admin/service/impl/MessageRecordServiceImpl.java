package com.oszero.deliver.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oszero.deliver.admin.mapper.MessageRecordMapper;
import com.oszero.deliver.admin.model.entity.MessageRecord;
import com.oszero.deliver.admin.service.MessageRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 针对表【message_record(消息记录)】的数据库操作Service实现
 *
 * @author oszero
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class MessageRecordServiceImpl extends ServiceImpl<MessageRecordMapper, MessageRecord>
        implements MessageRecordService {

    private final MessageRecordMapper messageRecordMapper;

    @Override
    public List<MessageRecord> getTemplateInfo(LocalDateTime startTime, LocalDateTime endTime, Integer size) {
        return messageRecordMapper.getTemplateInfo(startTime, endTime, size);
    }

    @Override
    public List<MessageRecord> getAppInfo(LocalDateTime startTime, LocalDateTime endTime, Integer size) {
        return messageRecordMapper.getAppInfo(startTime, endTime, size);
    }

    @Override
    public List<MessageRecord> getPushUserInfo(LocalDateTime startTime, LocalDateTime endTime, Integer size) {
        return messageRecordMapper.getPushUserInfo(startTime, endTime, size);
    }

}




