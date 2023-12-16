package com.oszero.deliver.server.model.event;

import com.oszero.deliver.server.model.dto.common.SendTaskDto;

/**
 * 飞书发布事件
 *
 * @author oszero
 * @version 1.0.0
 */
public class FeiShuEvent extends BaseApplicationEvent {

    public FeiShuEvent(Object source, SendTaskDto sendTaskDto) {
        super(source, sendTaskDto);
    }
}
