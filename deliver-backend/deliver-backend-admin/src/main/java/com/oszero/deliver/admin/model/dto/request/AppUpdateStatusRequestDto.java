package com.oszero.deliver.admin.model.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

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
