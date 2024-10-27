package com.oszero.deliver.admin.model.dto.request;

import lombok.Data;

/**
 * 分组保存和更新 DTO
 *
 * @author oszero
 * @version 1.0.0
 */
@Data
public class GroupSaveAndUpdateRequestDto {
    private Long groupId;
    private String groupName;
    private String groupDescription;
}
