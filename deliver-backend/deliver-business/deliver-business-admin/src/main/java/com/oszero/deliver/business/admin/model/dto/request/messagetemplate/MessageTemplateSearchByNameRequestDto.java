package com.oszero.deliver.business.admin.model.dto.request.messagetemplate;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author oszero
 * @version 1.0.0
 */
@Data
public class MessageTemplateSearchByNameRequestDto {
    @NotBlank(message = "模板名称不能为空")
    private String templateName;
}
