package com.oszero.deliver.admin.model.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * APP 状态更新请求 dto
 *
 * @author oszero
 * @version 1.0.0
 */
@Data
public class AppUpdateStatusRequestDto {
    /**
     * APPID
     */
    @NotNull
    private Long appId;

    /**
     * APP 状态
     */
    @NotNull
    private Integer appStatus;
}
