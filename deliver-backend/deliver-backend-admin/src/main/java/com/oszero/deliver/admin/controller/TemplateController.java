package com.oszero.deliver.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oszero.deliver.admin.model.common.CommonResult;
import com.oszero.deliver.admin.model.dto.request.*;
import com.oszero.deliver.admin.model.dto.response.MessageTypeResponseDto;
import com.oszero.deliver.admin.model.dto.response.TemplateSearchResponseDto;
import com.oszero.deliver.admin.service.TemplateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 模板控制器
 *
 * @author oszero
 * @version 1.0.0
 */
@Validated
@RestController
@RequestMapping("/template")
@RequiredArgsConstructor
public class TemplateController {

    private final TemplateService templateService;

    /**
     * 分页搜索模版
     *
     * @param templateSearchRequestDto 查询参数
     * @return Page
     */
    @PostMapping("/search")
    public CommonResult<Page<TemplateSearchResponseDto>> search(@RequestBody TemplateSearchRequestDto templateSearchRequestDto) {
        Page<TemplateSearchResponseDto> page = templateService.search(templateSearchRequestDto);
        return CommonResult.success(page);
    }

    /**
     * 保存模版
     *
     * @param dto 参数
     * @return 成功
     */
    @PostMapping("/saveTemplate")
    public CommonResult<?> saveTemplate(@Valid @RequestBody TemplateSaveAndUpdateRequestDto dto) {
        templateService.save(dto);
        return CommonResult.success();
    }

    /**
     * 更新通过 ID
     *
     * @param dto 参数
     * @return 成功
     */
    @PostMapping("/updateById")
    public CommonResult<?> updateById(@Valid @RequestBody TemplateSaveAndUpdateRequestDto dto) {
        templateService.updateById(dto);
        return CommonResult.success();
    }

    /**
     * 更新模版状态通过 ID
     *
     * @param dto 参数
     * @return 成功
     */
    @PostMapping("/updateStatusById")
    public CommonResult<?> updateStatusById(@Valid @RequestBody TemplateUpdateStatusRequestDto dto) {
        templateService.updateStatusById(dto);
        return CommonResult.success();
    }

    /**
     * 批量删除
     *
     * @param dto 参数
     * @return 成功
     */
    @PostMapping("/deleteByIds")
    public CommonResult<?> deleteByIds(@Valid @RequestBody DeleteIdsRequestDto dto) {
        templateService.deleteByIds(dto);
        return CommonResult.success();
    }

    /**
     * 获取消息类型通过渠道类型
     *
     * @param dto dto
     * @return 消息类型
     */
    @PostMapping("/getMessageTypeByChannelType")
    public CommonResult<List<MessageTypeResponseDto>> getMessageTypeByChannelType(@Valid @RequestBody TemplateAddGetByChannelRequestDto dto) {
        List<MessageTypeResponseDto> messageTypeResponseDtoList = templateService.getMessageTypeByChannelType(dto);
        return CommonResult.success(messageTypeResponseDtoList);
    }

    /**
     * 测试消息发送功能
     *
     * @param sendTestRequestDto 参数
     * @return 成功
     */
    @PostMapping("/testSendMessage")
    public CommonResult<?> testSendMessage(@Valid @RequestBody SendTestRequestDto sendTestRequestDto) {
        templateService.testSendMessage(sendTestRequestDto);
        return CommonResult.success();
    }

    @PostMapping("/getMessageParamByMessageType")
    public CommonResult<String> getMessageParamByMessageType(@Valid @RequestBody TemplateMessageParamByMessageTypeRequestDto dto) {
        return CommonResult.success(templateService.getMessageParamByMessageType(dto));
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
