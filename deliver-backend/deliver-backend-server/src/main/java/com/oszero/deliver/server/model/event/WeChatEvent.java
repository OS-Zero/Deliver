package com.oszero.deliver.server.model.event;

import com.oszero.deliver.server.model.dto.common.SendTaskDto;

/**
 * 企微发布事件
 *
 * @author oszero
 * @version 1.0.0
 */
public class WeChatEvent extends BaseApplicationEvent {

    public WeChatEvent(Object source, SendTaskDto sendTaskDto) {
        super(source, sendTaskDto);
    }
}
