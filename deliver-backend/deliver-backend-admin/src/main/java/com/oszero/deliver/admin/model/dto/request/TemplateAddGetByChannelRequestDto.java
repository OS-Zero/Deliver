package com.oszero.deliver.admin.model.dto.request;

import lombok.Data;

import jakarta.validation.constraints.*;

/**
 * 模版添加中-通过渠道类型获取请求 dto
 *
 * @author oszero
 * @version 1.0.0
 */
@Data
public class TemplateAddGetByChannelRequestDto {
    @NotNull
    private Integer channelType;
}
