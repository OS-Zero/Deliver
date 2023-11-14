package com.oszero.deliver.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oszero.deliver.admin.model.dto.request.AppSaveAndUpdateRequestDto;
import com.oszero.deliver.admin.model.dto.request.AppSearchRequestDto;
import com.oszero.deliver.admin.model.dto.request.DeleteIdsRequestDto;
import com.oszero.deliver.admin.model.dto.request.TemplateAddGetByChannelRequestDto;
import com.oszero.deliver.admin.model.dto.response.AppByChannelResponseDto;
import com.oszero.deliver.admin.model.dto.response.AppSearchResponseDto;
import com.oszero.deliver.admin.model.entity.App;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * app service
 *
 * @author oszero
 * @version 1.0.0
 */
public interface AppService extends IService<App> {

    Page<AppSearchResponseDto> getAppPagesByCondition(AppSearchRequestDto appSearchRequestDto);

    void deleteByIds(DeleteIdsRequestDto dto);

    void updateById(AppSaveAndUpdateRequestDto dto);

    void save(AppSaveAndUpdateRequestDto dto);

    List<AppByChannelResponseDto> getAppByChannelType(TemplateAddGetByChannelRequestDto dto);
}
