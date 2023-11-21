package com.oszero.deliver.admin.model.dto.response;

import lombok.Data;

/**
 * APP 通过渠道类型响应 dto
 *
 * @author oszero
 * @version 1.0.0
 */
@Data
public class AppByChannelResponseDto {
    private Long appId;
    private String appName;
}
