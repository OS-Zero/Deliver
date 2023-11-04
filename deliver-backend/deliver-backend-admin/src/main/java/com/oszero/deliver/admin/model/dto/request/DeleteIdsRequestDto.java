package com.oszero.deliver.admin.model.dto.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class DeleteIdsRequestDto {

    @NotEmpty
    List<Long> ids;
}
