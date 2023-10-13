package com.oszero.deliver.server.web.controller;

import com.oszero.deliver.server.model.dto.req.SendReqDto;
import com.oszero.deliver.server.model.dto.resp.CommonRespDto;
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
    public CommonRespDto<?> sendMessage(@Validated @RequestBody SendReqDto sendReqDto) {
        Integer send = sendService.send(sendReqDto);
        return CommonRespDto.success(send);
    }
}
