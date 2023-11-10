package com.oszero.deliver.admin.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 数据面板各种图形响应类
 *
 * @author oszero
 * @version 1.0.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DashboardInfoResponseDto {

    private List<DashboardInfo> dashboardInfoList;

    @Data
    public static class DashboardInfo {
        private Long value;
        private String name;
    }
}
