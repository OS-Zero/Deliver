package com.oszero.deliver.server.model.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = "消息模板 ID 不能为 NULL")
    private Long templateId;

    /**
     * 发送用户列表
     */
    @NotEmpty(message = "发送用户列表不能为空")
    private List<String> users;

    /**
     * 不同消息的不同参数
     */
    @NotNull(message = "消息参数不能为 NULL")
    private Map<String, Object> paramMap;

    /**
     * 重试次数
     * 默认为 0
     */
    private Integer retry = 0;
}
