package com.oszero.deliver.admin.model.dto.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 批量删除 DTO
 *
 * @author oszero
 * @version 1.0.0
 */
@Data
public class DeleteIdsRequestDto {

    @NotEmpty
    List<Long> ids;
}
