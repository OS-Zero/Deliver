package com.oszero.deliver.server.util;

import com.oszero.deliver.server.model.dto.common.SendTaskDto;

/**
 * ThreadLocal 工具类
 *
 * @author oszero
 * @version 1.0.0
 */
public class ThreadLocalUtils {
    private final static ThreadLocal<SendTaskDto> SEND_TASK_DTO_THREAD_LOCAL = new ThreadLocal<>();

    public static void setSendTaskDto(SendTaskDto sendTaskDto) {
        SEND_TASK_DTO_THREAD_LOCAL.set(sendTaskDto);
    }

    public static SendTaskDto getSendTaskDto() {
        return SEND_TASK_DTO_THREAD_LOCAL.get();
    }

    public static void clear() {
        SEND_TASK_DTO_THREAD_LOCAL.remove();
    }
}
