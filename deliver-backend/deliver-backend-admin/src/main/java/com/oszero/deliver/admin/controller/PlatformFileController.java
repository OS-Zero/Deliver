package com.oszero.deliver.admin.controller;

import com.oszero.deliver.admin.model.CommonResult;
import com.oszero.deliver.admin.model.dto.request.PlatformFileUploadRequestDto;
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

    @PostMapping("/uploadFile")
    public CommonResult<?> uploadFile(@Valid @RequestBody PlatformFileUploadRequestDto dto, @RequestParam("platformFile") MultipartFile platformFile) throws Exception {
        platformFileService.uploadFile(dto, platformFile);
        return CommonResult.success();
    }
}
