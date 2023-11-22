package com.oszero.deliver.sdk.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * 发送请求 DTO
 *
 * @author oszero
 * @version 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SendRequestDto {

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
     * 重试次数
     * 默认为 0
     */
    private Integer retry = 0;
}
