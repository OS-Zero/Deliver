package com.oszero.deliver.server.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PushWayDto {
    private Integer channelType;
    private Integer messageType;
}
