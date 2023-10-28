package com.oszero.deliver.server.web.controller;

import com.oszero.deliver.server.model.CommonResult;
import com.oszero.deliver.server.model.dto.request.SendRequestDto;
import com.oszero.deliver.server.web.service.SendService;
import com.taobao.api.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 开放接口
 *
 * @author oszero
 * @version 1.0.0
 */
@Validated
@RestController
@RequestMapping("/open")
@RequiredArgsConstructor
public class OpenController {

    private final SendService sendService;

    @PostMapping("/sendMessage")
    public CommonResult<?> sendMessage(@Valid @RequestBody SendRequestDto sendRequestDto) {
        Integer send = sendService.send(sendRequestDto);
        return CommonResult.success(send);
    }
}
