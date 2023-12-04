package com.oszero.deliver.server.web.service;

import com.oszero.deliver.server.model.dto.request.SendRequestDto;

/**
 * 发送消息操作 Service
 *
 * @author oszero
 * @version 1.0.0
 */
public interface SendService {
    String send(SendRequestDto sendRequestDto) ;
}
