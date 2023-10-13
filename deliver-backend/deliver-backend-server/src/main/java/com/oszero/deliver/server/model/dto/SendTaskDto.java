package com.oszero.deliver.server.model.dto;

import com.oszero.deliver.server.pretreatment.pipeline.ProcessModel;
import lombok.*;

import java.util.List;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SendTaskDto extends ProcessModel {

    /**
     * 消息模板 Id
     */
    private Long templateId;
    /**
     * 发送用户列表
     */
    private List<String> users;

    /**
     * 不同消息的不同参数
     */
    private Map<String, String> paramMap;

    private Integer appId;
    private Long logId;
    private Integer sendRange;
    private Integer usersType;
    private Integer channelType;
    private Integer messageType;
    private Integer retry;

}


