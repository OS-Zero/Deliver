package com.oszero.deliver.server.model.dto;

import com.oszero.deliver.server.pretreatment.link.LinkModel;
import lombok.*;

import java.util.List;
import java.util.Map;

/**
 * 发送消息任务 DTO
 *
 * @author oszero
 * @version 1.0.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SendTaskDto extends LinkModel {

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
    private Map<String, Object> paramMap;

    /**
     * 转换后的参数 JSON 便于后续直接转换为对应参数
     */
    private String paramJson;

    /**
     * appConfig 数据库的 JSON 数据
     */
    private String appConfigJson;

    /**
     * 发送范围
     */
    private Integer pushRange;

    /**
     * 发送用户类型
     */
    private Integer usersType;

    /**
     * 渠道类型
     */
    private Integer channelType;

    /**
     * 消息类型
     */
    private String messageType;

    /**
     * 失败重试次数，默认为 0
     */
    private Integer retry = 0;
}


