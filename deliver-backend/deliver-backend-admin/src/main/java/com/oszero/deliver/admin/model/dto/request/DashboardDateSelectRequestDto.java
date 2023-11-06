package com.oszero.deliver.admin.model.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DashboardDateSelectRequestDto {
    @NotNull
    private Integer dateSelect;
}
