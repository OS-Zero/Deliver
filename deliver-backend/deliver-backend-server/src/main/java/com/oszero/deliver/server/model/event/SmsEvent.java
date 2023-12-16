package com.oszero.deliver.server.model.event;

import com.oszero.deliver.server.model.dto.common.SendTaskDto;

/**
 * 短信发布事件
 *
 * @author oszero
 * @version 1.0.0
 */
public class SmsEvent extends BaseApplicationEvent {

    public SmsEvent(Object source, SendTaskDto sendTaskDto) {
        super(source, sendTaskDto);
    }
}
