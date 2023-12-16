package com.oszero.deliver.server.model.event;

import com.oszero.deliver.server.model.dto.common.SendTaskDto;

/**
 * 电话发布事件
 *
 * @author oszero
 * @version 1.0.0
 */
public class CallEvent extends BaseApplicationEvent {
    public CallEvent(Object source, SendTaskDto sendTaskDto) {
        super(source, sendTaskDto);
    }
}
