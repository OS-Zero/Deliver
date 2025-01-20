package com.oszero.deliver.business.admin.model.dto.request.peoplegroup;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author oszero
 * @version 1.0.0
 */
@Data
public class PeopleGroupSearchByNameRequestDto {
    @NotBlank(message = "人群名称不能为空")
    private String peopleGroupName;
}
