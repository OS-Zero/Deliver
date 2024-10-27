package com.oszero.deliver.admin.model.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 获取所有分组 Dto
 *
 * @author oszero
 * @version 1.0.0
 */
@Data
public class GroupGetAllResponseDto {
    private Long groupId;
    private String groupName;
    private String groupDescription;
    private String createUser;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
