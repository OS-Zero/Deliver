package com.oszero.deliver.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oszero.deliver.admin.model.CommonResult;
import com.oszero.deliver.admin.model.dto.request.PlatformFileSearchRequestDto;
import com.oszero.deliver.admin.model.dto.request.PlatformFileUploadRequestDto;
import com.oszero.deliver.admin.model.dto.response.PlatformFileSearchResponseDto;
import com.oszero.deliver.admin.service.PlatformFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequestMapping("/platformFile")
@RequiredArgsConstructor
public class PlatformFileController {

    private final PlatformFileService platformFileService;

    @PostMapping("/getPagePlatformFile")
    public CommonResult<Page<PlatformFileSearchResponseDto>> getPagePlatformFile(@RequestBody PlatformFileSearchRequestDto dto) {
        Page<PlatformFileSearchResponseDto> platformFileSearchResponseDtoPage = platformFileService.getPagePlatformFile(dto);
        return CommonResult.success(platformFileSearchResponseDtoPage);
    }

    @PostMapping("/uploadFile")
    public CommonResult<?> uploadFile(@Valid PlatformFileUploadRequestDto dto, @RequestParam("platformFile") MultipartFile platformFile) throws Exception {
        platformFileService.uploadFile(dto, platformFile);
        return CommonResult.success();
    }
}
