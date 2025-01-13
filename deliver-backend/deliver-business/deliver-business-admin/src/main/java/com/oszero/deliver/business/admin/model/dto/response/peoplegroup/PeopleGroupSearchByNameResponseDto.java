package com.oszero.deliver.business.admin.model.dto.response.peoplegroup;

import lombok.Builder;
import lombok.Data;

/**
 * @author oszero
 * @version 1.0.0
 */
@Data
@Builder
public class PeopleGroupSearchByNameResponseDto {
    private Long peopleGroupId;
    private String peopleGroupName;
}
