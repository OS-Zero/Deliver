package com.oszero.deliver.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oszero.deliver.admin.model.CommonResult;
import com.oszero.deliver.admin.model.dto.request.DeleteIdsRequestDto;
import com.oszero.deliver.admin.model.dto.request.TemplateAddGetByChannelRequestDto;
import com.oszero.deliver.admin.model.dto.request.TemplateSaveAndUpdateRequestDto;
import com.oszero.deliver.admin.model.dto.request.TemplateSearchRequestDto;
import com.oszero.deliver.admin.model.dto.response.MessageTypeResponseDto;
import com.oszero.deliver.admin.model.dto.response.TemplateSearchResponseDto;
import com.oszero.deliver.admin.service.TemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

/**
 * 模板控制器
 *
 * @author oszero
 * @version 1.0.0
 */
@RestController
@RequestMapping("/template")
@RequiredArgsConstructor
public class TemplateController {

    private final TemplateService templateService;

    @PostMapping("/search")
    public CommonResult<Page<TemplateSearchResponseDto>> search(@RequestBody TemplateSearchRequestDto templateSearchRequestDto) {
        Page<TemplateSearchResponseDto> page = templateService.search(templateSearchRequestDto);
        return CommonResult.success(page);
    }

    @PostMapping("/saveTemplate")
    public CommonResult<?> saveTemplate(@Valid @RequestBody TemplateSaveAndUpdateRequestDto dto) {
        templateService.save(dto);
        return CommonResult.success();
    }

    @PostMapping("/updateById")
    public CommonResult<?> updateById(@Valid @RequestBody TemplateSaveAndUpdateRequestDto dto) {
        templateService.updateById(dto);
        return CommonResult.success();
    }

    @PostMapping("/deleteByIds")
    public CommonResult<?> deleteByIds(@Valid @RequestBody DeleteIdsRequestDto dto) {
        templateService.deleteByIds(dto);
        return CommonResult.success();
    }

    @PostMapping("/getMessageTypeByChannelType")
    public CommonResult<List<MessageTypeResponseDto>> getMessageTypeByChannelType(@Valid @RequestBody TemplateAddGetByChannelRequestDto dto) {
        List<MessageTypeResponseDto> messageTypeResponseDtoList = templateService.getMessageTypeByChannelType(dto);
        return CommonResult.success(messageTypeResponseDtoList);
    }

    /**
     * excel 批量导入模板
     *
     * @param multipartFile 文件对象
     * @return 通用响应
     */
    @PostMapping("/fileBatchSave")
    public CommonResult<?> fileBatchSave(@RequestParam("excel") MultipartFile multipartFile) {

        return CommonResult.success();
    }
}
