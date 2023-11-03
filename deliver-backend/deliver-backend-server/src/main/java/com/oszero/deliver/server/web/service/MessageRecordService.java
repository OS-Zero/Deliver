package com.oszero.deliver.server.web.service;

import com.oszero.deliver.server.enums.StatusEnum;
import com.oszero.deliver.server.model.dto.SendTaskDto;
import com.oszero.deliver.server.model.entity.MessageRecord;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 针对表【message_record(消息记录)】的数据库操作Service
 *
 * @author oszero
 * @version 1.0.0
 */
public interface MessageRecordService extends IService<MessageRecord> {
    void saveMessageRecord(SendTaskDto sendTaskDto, StatusEnum messageStatus, String user);
}
