package com.oszero.deliver.server.model.event;

import com.oszero.deliver.server.model.dto.common.SendTaskDto;

/**
 * 钉钉发布事件
 *
 * @author oszero
 * @version 1.0.0
 */
public class DingEvent extends BaseApplicationEvent {

    public DingEvent(Object source, SendTaskDto sendTaskDto) {
        super(source, sendTaskDto);
    }
}
