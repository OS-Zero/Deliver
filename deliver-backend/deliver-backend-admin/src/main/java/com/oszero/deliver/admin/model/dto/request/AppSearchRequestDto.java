package com.oszero.deliver.admin.model.dto.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * app 查询 dto
 *
 * @author oszero
 * @version 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AppSearchRequestDto extends PageRequest {

    private String appName;
    private Integer channelType;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

}
