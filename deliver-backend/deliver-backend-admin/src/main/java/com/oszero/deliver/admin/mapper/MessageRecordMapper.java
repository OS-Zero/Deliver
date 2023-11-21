package com.oszero.deliver.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.oszero.deliver.admin.model.entity.MessageRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 针对表【message_record(消息记录)】的数据库操作Mapper
 *
 * @author oszero
 * @version 1.0.0
 */
@Mapper
public interface MessageRecordMapper extends BaseMapper<MessageRecord> {

    /**
     * 获取指定时间内的 TOP size
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param size      大小
     * @return 消息记录
     */
    @Select(
            "select template_id, count(*) as value from message_record where create_time >= #{startTime} and create_time <= #{endTime} group by template_id order by value desc limit 0, #{size};"
    )
    List<MessageRecord> getTemplateInfo(LocalDateTime startTime, LocalDateTime endTime, Integer size);

    /**
     * 获取指定时间内的 TOP size
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param size      大小
     * @return 消息记录
     */
    @Select(
            "select app_id, count(*) as value from message_record where create_time >= #{startTime} and create_time <= #{endTime} group by app_id order by value desc limit 0, #{size};"
    )
    List<MessageRecord> getAppInfo(LocalDateTime startTime, LocalDateTime endTime, Integer size);

    /**
     * 获取指定时间内的 TOP size
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param size      大小
     * @return 消息记录
     */
    @Select(
            "select push_user, count(*) as value from message_record where create_time >= #{startTime} and create_time <= #{endTime} group by push_user order by value desc limit 0, #{size};"
    )
    List<MessageRecord> getPushUserInfo(LocalDateTime startTime, LocalDateTime endTime, Integer size);
}




