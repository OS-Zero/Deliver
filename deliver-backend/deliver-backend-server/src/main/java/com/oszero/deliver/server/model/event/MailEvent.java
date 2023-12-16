package com.oszero.deliver.server.model.event;

import com.oszero.deliver.server.model.dto.common.SendTaskDto;

/**
 * 邮件发布事件
 *
 * @author oszero
 * @version 1.0.0
 */
public class MailEvent extends BaseApplicationEvent {

    public MailEvent(Object source, SendTaskDto sendTaskDto) {
        super(source, sendTaskDto);
    }
}
