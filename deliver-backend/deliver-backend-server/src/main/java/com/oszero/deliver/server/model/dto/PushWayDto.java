package com.oszero.deliver.server.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 推送方式 DTO
 *
 * @author oszero
 * @version 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PushWayDto {
    private Integer channelType;
    private String messageType;
}
