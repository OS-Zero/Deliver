package com.oszero.deliver.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oszero.deliver.admin.model.CommonResult;
import com.oszero.deliver.admin.model.dto.request.PlatformFileSearchRequestDto;
import com.oszero.deliver.admin.model.dto.request.PlatformFileUploadRequestDto;
import com.oszero.deliver.admin.model.dto.response.PlatformFileSearchResponseDto;
import com.oszero.deliver.admin.service.PlatformFileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 平台文件控制器
 *
 * @author oszero
 * @version 1.0.0
 */
@Validated
@RestController
@RequestMapping("/platformFile")
@RequiredArgsConstructor
public class PlatformFileController {

    private final PlatformFileService platformFileService;

    /**
     * 分页查询平台文件
     *
     * @param dto 查询参数
     * @return Page
     */
    @PostMapping("/getPagePlatformFile")
    public CommonResult<Page<PlatformFileSearchResponseDto>> getPagePlatformFile(@RequestBody PlatformFileSearchRequestDto dto) {
        Page<PlatformFileSearchResponseDto> platformFileSearchResponseDtoPage = platformFileService.getPagePlatformFile(dto);
        return CommonResult.success(platformFileSearchResponseDtoPage);
    }

    /**
     * 上传平台文件
     *
     * @param dto 参数
     * @return 成功
     * @throws Exception 异常
     */
    @PostMapping("/uploadFile")
    public CommonResult<?> uploadFile(@Valid PlatformFileUploadRequestDto dto) throws Exception {
        platformFileService.uploadFile(dto);
        return CommonResult.success();
    }
}
