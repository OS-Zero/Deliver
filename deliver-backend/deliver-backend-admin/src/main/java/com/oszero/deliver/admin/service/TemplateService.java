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

    Page<TemplateSearchResponseDto> search(TemplateSearchRequestDto templateSearchRequestDto);

    void deleteByIds(DeleteIdsRequestDto dto);

    void updateById(TemplateSaveAndUpdateRequestDto dto);

    void updateStatusById(TemplateUpdateStatusRequestDto dto);

    void save(TemplateSaveAndUpdateRequestDto dto);

    List<MessageTypeResponseDto> getMessageTypeByChannelType(TemplateAddGetByChannelRequestDto dto);
}
