package com.oszero.deliver.business.server.util;

import com.lmax.disruptor.RingBuffer;
import com.oszero.deliver.business.server.model.dto.common.SendTaskDto;
import com.oszero.deliver.business.server.model.event.disruptor.DisruptorBaseEvent;

/**
 * @author oszero
 * @version 1.0.0
 */
public class DisruptorUtils {

    public static void publishEvent(SendTaskDto sendTaskDto, RingBuffer<? extends DisruptorBaseEvent> ringBuffer) {
        long sequence = ringBuffer.next();  // 获取下一个事件的序列号
        try {
            DisruptorBaseEvent event = ringBuffer.get(sequence);  // 获取该位置的事件对象
            event.setSendTaskDto(sendTaskDto);  // 设置事件数据
        } finally {
            ringBuffer.publish(sequence);  // 发布事件
        }
    }
}
