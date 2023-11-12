package com.oszero.deliver.admin.model.entity;

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
public class PushWay {
    private String channelType;
    private String messageType;
}
