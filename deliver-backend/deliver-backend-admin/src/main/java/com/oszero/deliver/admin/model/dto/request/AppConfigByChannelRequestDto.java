package com.oszero.deliver.admin.model.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 通过渠道类型获取 APP 配置请求 DTO
 *
 * @author oszero
 * @version 1.0.0
 */
@Data
public class AppConfigByChannelRequestDto {
    @NotNull
    private Integer channelType;
}
