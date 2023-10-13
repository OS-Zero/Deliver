package com.oszero.deliver.server.web.controller;

import com.oszero.deliver.server.model.CommonResult;
import com.oszero.deliver.server.model.dto.request.SendRequestDto;
import com.oszero.deliver.server.web.service.SendService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/open")
@RequiredArgsConstructor
public class OpenController {

    private final SendService sendService;

    @PostMapping("/sendMessage")
    public CommonResult<?> sendMessage(@Validated @RequestBody SendRequestDto sendRequestDto) {
        Integer send = sendService.send(sendRequestDto);
        return CommonResult.success(send);
    }
}
