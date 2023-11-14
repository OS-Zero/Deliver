package com.oszero.deliver.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oszero.deliver.admin.model.dto.request.DeleteIdsRequestDto;
import com.oszero.deliver.admin.model.dto.request.TemplateAddGetByChannelRequestDto;
import com.oszero.deliver.admin.model.dto.request.TemplateSaveAndUpdateRequestDto;
import com.oszero.deliver.admin.model.dto.request.TemplateSearchRequestDto;
import com.oszero.deliver.admin.model.dto.response.MessageTypeResponseDto;
import com.oszero.deliver.admin.model.dto.response.TemplateSearchResponseDto;
import com.oszero.deliver.admin.model.entity.Template;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 模板 service
 *
 * @author oszero
 * @version 1.0.0
 */
public interface TemplateService extends IService<Template> {

    Page<TemplateSearchResponseDto> search(TemplateSearchRequestDto templateSearchRequestDto);

    void deleteByIds(DeleteIdsRequestDto dto);

    void updateById(TemplateSaveAndUpdateRequestDto dto);

    void save(TemplateSaveAndUpdateRequestDto dto);

    List<MessageTypeResponseDto> getMessageTypeByChannelType(TemplateAddGetByChannelRequestDto dto);
}
