package com.oszero.deliver.server.model.event;

import com.oszero.deliver.server.model.dto.common.SendTaskDto;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * 事件抽象类
 *
 * @author oszero
 * @version 1.0.0
 */
@Getter
public abstract class BaseApplicationEvent extends ApplicationEvent {

    private final SendTaskDto sendTaskDto;

    public BaseApplicationEvent(Object source, SendTaskDto sendTaskDto) {
        super(source);
        this.sendTaskDto = sendTaskDto;
    }
}
