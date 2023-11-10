package com.oszero.deliver.admin.controller;

import com.oszero.deliver.admin.model.CommonResult;
import com.oszero.deliver.admin.model.dto.request.DashboardDateSelectRequestDto;
import com.oszero.deliver.admin.model.dto.response.*;
import com.oszero.deliver.admin.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 仪表盘
 *
 * @author oszero
 * @version 1.0.0
 */
@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @PostMapping("/getDashboardHeadData")
    public CommonResult<DashboardHeadDataResponseDto> getDashboardHeadData() {
        DashboardHeadDataResponseDto dto = dashboardService.getDashboardHeadData();
        return CommonResult.success(dto);
    }

    @PostMapping("/getMessageInfo")
    public CommonResult<MessageInfoResponseDto> getMessageInfo(@Valid @RequestBody DashboardDateSelectRequestDto dto) {
        MessageInfoResponseDto messageInfoResponseDto = dashboardService.getMessageInfo(dto);
        return CommonResult.success(messageInfoResponseDto);
    }

    @PostMapping("/getTemplateInfo")
    public CommonResult<DashboardInfoResponseDto> getTemplateInfo(@Valid @RequestBody DashboardDateSelectRequestDto dto) {
        DashboardInfoResponseDto dashboardInfoResponseDto = dashboardService.getTemplateInfo(dto);
        return CommonResult.success(dashboardInfoResponseDto);
    }

    @PostMapping("/getAppInfo")
    public CommonResult<DashboardInfoResponseDto> getAppInfo(@Valid @RequestBody DashboardDateSelectRequestDto dto) {
        DashboardInfoResponseDto appInfoResponseDto = dashboardService.getAppInfo(dto);
        return CommonResult.success(appInfoResponseDto);
    }

    @PostMapping("/getPushUserInfo")
    public CommonResult<DashboardInfoResponseDto> getPushUserInfo(@Valid @RequestBody DashboardDateSelectRequestDto dto) {
        DashboardInfoResponseDto pushUserInfoResponseDto = dashboardService.getPushUserInfo(dto);
        return CommonResult.success(pushUserInfoResponseDto);
    }
}
