package com.oszero.deliver.admin.model.dto.request;

import lombok.Data;

import jakarta.validation.constraints.NotNull;

/**
 * 模版状态更新 dto
 *
 * @author oszero
 * @version 1.0.0
 */
@Data
public class TemplateUpdateStatusRequestDto {
    /**
     * 模板id
     */
    @NotNull
    private Long templateId;

    /**
     * 模板状态
     */
    @NotNull
    private Integer templateStatus;
}
