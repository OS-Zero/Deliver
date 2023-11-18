package com.oszero.deliver.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oszero.deliver.admin.model.CommonResult;
import com.oszero.deliver.admin.model.dto.request.*;
import com.oszero.deliver.admin.model.dto.response.AppByChannelResponseDto;
import com.oszero.deliver.admin.model.dto.response.AppSearchResponseDto;
import com.oszero.deliver.admin.service.AppService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * APP 控制器
 *
 * @author oszero
 * @version 1.0.0
 */
@RestController
@RequestMapping("/app")
@RequiredArgsConstructor
public class AppController {

    private final AppService appService;

    @PostMapping("/search")
    public CommonResult<Page<AppSearchResponseDto>> getAppPagesByCondition(@RequestBody AppSearchRequestDto appSearchRequestDto) {
        Page<AppSearchResponseDto> page = appService.getAppPagesByCondition(appSearchRequestDto);
        return CommonResult.success(page);
    }

    @PostMapping("/save")
    public CommonResult<?> save(@RequestBody AppSaveAndUpdateRequestDto dto) {
        appService.save(dto);
        return CommonResult.success();
    }

    @PostMapping("/updateById")
    public CommonResult<?> updateById(@RequestBody AppSaveAndUpdateRequestDto dto) {
        appService.updateById(dto);
        return CommonResult.success();
    }

    @PostMapping("/updateStatusById")
    public CommonResult<?> updateStatusById(@RequestBody AppUpdateStatusRequestDto dto) {
        appService.updateStatusById(dto);
        return CommonResult.success();
    }

    @PostMapping("/deleteByIds")
    public CommonResult<?> deleteByIds(@RequestBody DeleteIdsRequestDto dto) {
        appService.deleteByIds(dto);
        return CommonResult.success();
    }

    @PostMapping("/getAppByChannelType")
    public CommonResult<List<AppByChannelResponseDto>> getAppByChannelType(@Valid @RequestBody TemplateAddGetByChannelRequestDto dto) {
        List<AppByChannelResponseDto> appList = appService.getAppByChannelType(dto);
        return CommonResult.success(appList);
    }
}
