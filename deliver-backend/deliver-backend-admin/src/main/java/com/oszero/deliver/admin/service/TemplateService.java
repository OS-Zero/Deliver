package com.oszero.deliver.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.oszero.deliver.admin.model.dto.request.*;
import com.oszero.deliver.admin.model.dto.response.MessageTypeResponseDto;
import com.oszero.deliver.admin.model.dto.response.TemplateSearchResponseDto;
import com.oszero.deliver.admin.model.entity.Template;

import java.util.List;

/**
 * 模板 service
 *
 * @author oszero
 * @version 1.0.0
 */
public interface TemplateService extends IService<Template> {

    /**
     * 分页搜索模版
     *
     * @param templateSearchRequestDto 参数
     * @return Page
     */
    Page<TemplateSearchResponseDto> search(TemplateSearchRequestDto templateSearchRequestDto);

    /**
     * 批量删除
     *
     * @param dto 参数
     */
    void deleteByIds(DeleteIdsRequestDto dto);

    /**
     * 更新通过 ID
     *
     * @param dto 参数
     */
    void updateById(TemplateSaveAndUpdateRequestDto dto);

    /**
     * 更新状态
     *
     * @param dto 参数
     */
    void updateStatusById(TemplateUpdateStatusRequestDto dto);

    /**
     * 保存
     *
     * @param dto 参数
     */
    void save(TemplateSaveAndUpdateRequestDto dto);

    /**
     * 获取消息类型通过渠道
     *
     * @param dto 参数
     * @return 消息
     */
    List<MessageTypeResponseDto> getMessageTypeByChannelType(TemplateAddGetByChannelRequestDto dto);

    /**
     * 测试发送消息
     *
     * @param sendTestRequestDto 参数
     */
    void testSendMessage(SendTestRequestDto sendTestRequestDto);
}
