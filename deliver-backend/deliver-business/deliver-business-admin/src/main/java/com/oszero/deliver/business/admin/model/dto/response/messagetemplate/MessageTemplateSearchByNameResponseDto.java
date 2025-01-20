package com.oszero.deliver.business.admin.model.dto.response.messagetemplate;

import lombok.Builder;
import lombok.Data;

/**
 * @author oszero
 * @version 1.0.0
 */
@Data
@Builder
public class MessageTemplateSearchByNameResponseDto {
    private Long templateId;
    private String templateName;
}
