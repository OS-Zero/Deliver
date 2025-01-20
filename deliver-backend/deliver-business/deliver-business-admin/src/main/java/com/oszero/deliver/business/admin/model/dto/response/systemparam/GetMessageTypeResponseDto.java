package com.oszero.deliver.business.admin.model.dto.response.systemparam;

import lombok.Builder;
import lombok.Data;

/**
 * @author oszero
 * @version 1.0.0
 */
@Data
@Builder
public class GetMessageTypeResponseDto {
    private String messageType;
    private String messageTypeName;
}
