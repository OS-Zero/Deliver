package com.oszero.deliver.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oszero.deliver.admin.model.CommonResult;
import com.oszero.deliver.admin.model.dto.request.AppSearchRequestDto;
import com.oszero.deliver.admin.model.dto.response.AppSearchResponseDto;
import com.oszero.deliver.admin.service.AppService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * app 控制器
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
        appService.getAppPagesByCondition(appSearchRequestDto);
        return CommonResult.success();
    }
}
