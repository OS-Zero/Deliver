package com.oszero.deliver.admin.model.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 数据面板各图选择时间请求 DTO
 *
 * @author oszero
 * @version 1.0.0
 */
@Data
public class DashboardDateSelectRequestDto {
    @NotNull
    private Integer dateSelect;
}
