package com.oszero.deliver.admin.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 数据面板头数据响应类
 *
 * @author oszero
 * @version 1.0.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DashboardHeadDataResponseDto {
    private String numberOfMessagesToday;
    private String numberOfPlatformFiles;
    private String accumulatedTemplateOwnership;
    private String numberOfApps;
}
