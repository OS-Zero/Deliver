package com.oszero.deliver.admin.model.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class DeleteIdsRequestDto {

    List<Long> ids;
}
