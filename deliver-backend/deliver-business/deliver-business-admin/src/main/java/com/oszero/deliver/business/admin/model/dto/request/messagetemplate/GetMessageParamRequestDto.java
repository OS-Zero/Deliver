package com.oszero.deliver.business.admin.model.dto.request.messagetemplate;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author oszero
 * @version 1.0.0
 */
@Data
public class GetMessageParamRequestDto {
    @NotNull(message = "模板ID不能为空")
    private Long templateId;
}
