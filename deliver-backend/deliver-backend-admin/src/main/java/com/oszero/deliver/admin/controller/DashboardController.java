package com.oszero.deliver.admin.controller;

import com.oszero.deliver.admin.model.CommonResult;
import com.oszero.deliver.admin.model.dto.request.DashboardDateSelectRequestDto;
import com.oszero.deliver.admin.model.dto.response.DashboardHeadDataResponseDto;
import com.oszero.deliver.admin.model.dto.response.DashboardInfoResponseDto;
import com.oszero.deliver.admin.model.dto.response.MessageInfoResponseReactDto;
import com.oszero.deliver.admin.model.dto.response.MessageInfoResponseVueDto;
import com.oszero.deliver.admin.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

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

    @PostMapping("/getMessageInfo/vue")
    public CommonResult<MessageInfoResponseVueDto> getMessageInfoVue(@Valid @RequestBody DashboardDateSelectRequestDto dto) {
        MessageInfoResponseVueDto messageInfoResponseVueDto = dashboardService.getMessageInfoVue(dto);
        return CommonResult.success(messageInfoResponseVueDto);
    }

    @PostMapping("/getMessageInfo/react")
    public CommonResult<List<MessageInfoResponseReactDto>> getMessageInfoReact(@Valid @RequestBody DashboardDateSelectRequestDto dto) {
        List<MessageInfoResponseReactDto> messageInfoResponseReactDtoList = dashboardService.getMessageInfoReact(dto);
        return CommonResult.success(messageInfoResponseReactDtoList);
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
