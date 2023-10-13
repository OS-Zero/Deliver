package com.oszero.deliver.server.web.service;

import com.oszero.deliver.server.model.dto.request.SendRequestDto;

public interface SendService {
    Integer send(SendRequestDto sendRequestDto);
}
