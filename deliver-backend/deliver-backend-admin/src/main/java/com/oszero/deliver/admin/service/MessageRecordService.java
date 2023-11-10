package com.oszero.deliver.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.oszero.deliver.admin.model.entity.MessageRecord;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 针对表【message_record(消息记录)】的数据库操作Service
 *
 * @author oszero
 * @version 1.0.0
 */
public interface MessageRecordService extends IService<MessageRecord> {
    List<MessageRecord> getTemplateInfo(LocalDateTime startTime, LocalDateTime endTime, Integer size);
    List<MessageRecord> getAppInfo(LocalDateTime startTime, LocalDateTime endTime, Integer size);
    List<MessageRecord> getPushUserInfo(LocalDateTime startTime, LocalDateTime endTime, Integer size);
}
