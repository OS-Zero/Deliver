package com.oszero.deliver.admin.model.dto.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class AppSearchRequestDto extends PageRequest {

    private String appName;
    private Integer channelType;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

}
