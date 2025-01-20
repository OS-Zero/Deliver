package com.oszero.deliver.business.admin.model.dto.response.systemparam;

import lombok.Builder;
import lombok.Data;

/**
 * @author oszero
 * @version 1.0.0
 */
@Data
@Builder
public class GetChannelProviderTypeResponseDto {
    private Integer channelProviderType;
    private String channelProviderTypeName;
}
