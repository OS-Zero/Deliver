package com.oszero.deliver.server.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SendRequestDto {

    /**
     * 消息模板 Id
     */
    @NotNull
    private Integer templateId;

    /**
     * 发送用户列表
     */
    @NotEmpty
    private List<String> users;

    /**
     * 不同消息的不同参数
     */
    private Map<String, Object> paramMap;

    /**
     * 重试次数
     * 默认为0
     */
    private Integer retry;
}
