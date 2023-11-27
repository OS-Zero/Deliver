package com.oszero.deliver.admin.model.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 测试消息模版-获取消息参数请求 DTO
 *
 * @author oszero
 * @version 1.0.0
 */
@Data
public class TemplateMessageParamByMessageTypeRequestDto {
    @NotBlank
    private String messageType;
    @NotNull
    private Integer channelType;
}
