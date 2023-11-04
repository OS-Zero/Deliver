package com.oszero.deliver.admin.model.dto.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 模板查询 dto
 *
 * @author oszero
 * @version 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TemplateSearchRequestDto extends PageRequest {
    private String templateName;
    private Integer pushRange;
    private Integer usersType;
    private Integer templateStatus;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
