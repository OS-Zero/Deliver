package com.oszero.deliver.admin.model.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

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
