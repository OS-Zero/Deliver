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
    /**
     * 获取模版详情
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param size      大小
     * @return 消息记录
     */
    List<MessageRecord> getTemplateInfo(LocalDateTime startTime, LocalDateTime endTime, Integer size);

    /**
     * 获取 APP 详情
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param size      大小
     * @return 消息记录
     */
    List<MessageRecord> getAppInfo(LocalDateTime startTime, LocalDateTime endTime, Integer size);

    /**
     * 获取推送用户详情
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param size      大小
     * @return 消息记录
     */
    List<MessageRecord> getPushUserInfo(LocalDateTime startTime, LocalDateTime endTime, Integer size);
}
