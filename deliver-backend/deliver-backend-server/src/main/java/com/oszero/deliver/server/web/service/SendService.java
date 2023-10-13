package com.oszero.deliver.server.web.service;

import com.oszero.deliver.server.model.dto.req.SendReqDto;

public interface SendService {
    Integer send(SendReqDto sendReqDto);
}
