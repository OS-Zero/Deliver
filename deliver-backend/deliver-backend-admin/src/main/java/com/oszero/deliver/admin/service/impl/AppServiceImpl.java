package com.oszero.deliver.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oszero.deliver.admin.exception.BusinessException;
import com.oszero.deliver.admin.model.dto.request.AppSaveAndUpdateRequestDto;
import com.oszero.deliver.admin.model.dto.request.AppSearchRequestDto;
import com.oszero.deliver.admin.model.dto.request.DeleteIdsRequestDto;
import com.oszero.deliver.admin.model.dto.response.AppSearchResponseDto;
import com.oszero.deliver.admin.model.entity.App;
import com.oszero.deliver.admin.model.entity.Template;
import com.oszero.deliver.admin.model.entity.TemplateApp;
import com.oszero.deliver.admin.service.AppService;
import com.oszero.deliver.admin.mapper.AppMapper;
import com.oszero.deliver.admin.service.TemplateAppService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * app serviceImpl
 *
 * @author oszero
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class AppServiceImpl extends ServiceImpl<AppMapper, App>
        implements AppService {

    private final TemplateAppService templateAppService;

    @Override
    public Page<AppSearchResponseDto> getAppPagesByCondition(AppSearchRequestDto dto) {
        LambdaQueryWrapper<App> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StrUtil.isNotBlank(dto.getAppName()), App::getAppName, dto.getAppName())
                .eq(!Objects.isNull(dto.getChannelType()), App::getChannelType, dto.getChannelType())
                .gt(!Objects.isNull(dto.getStartTime()), App::getCreateTime, dto.getStartTime())
                .lt(!Objects.isNull(dto.getStartTime()), App::getCreateTime, dto.getEndTime());

        Page<App> appPage = new Page<>(dto.getCurrentPage(), dto.getPageSize());
        this.page(appPage, wrapper);
        List<AppSearchResponseDto> collect = appPage.getRecords().stream()
                .map(app -> {
                    AppSearchResponseDto appSearchResponseDto = new AppSearchResponseDto();
                    BeanUtil.copyProperties(app, appSearchResponseDto);
                    return appSearchResponseDto;
                }).collect(Collectors.toList());

        Page<AppSearchResponseDto> page = new Page<>(appPage.getPages(), appPage.getSize());
        page.setRecords(collect);
        page.setTotal(appPage.getTotal());
        return page;
    }

    @Override
    public void deleteByIds(DeleteIdsRequestDto dto) {
        ArrayList<Long> errRes = new ArrayList<>();
        dto.getIds().forEach(appId -> {
            LambdaQueryWrapper<TemplateApp> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(TemplateApp::getAppId, appId);
            List<TemplateApp> list = templateAppService.list(wrapper);
            if (!CollUtil.isEmpty(list)) {
                errRes.add(appId);
            }
        });
        if (!CollUtil.isEmpty(errRes)) {
            throw new BusinessException("以下 app 已关联模板，请解除关联关系后再删除：" + errRes);
        }
        boolean b = this.removeBatchByIds(dto.getIds());
        if (!b) {
            throw new BusinessException("删除 app 失败！！！");
        }
    }

    @Override
    public void updateById(AppSaveAndUpdateRequestDto dto) {
        App app = new App();
        BeanUtil.copyProperties(dto, app);
        boolean update = this.updateById(app);
        if (!update) {
            throw new BusinessException("app 更新失败！！！");
        }
    }

    @Override
    public void save(AppSaveAndUpdateRequestDto dto) {
        App app = new App();
        BeanUtil.copyProperties(dto, app);
        boolean save = this.save(app);
        if (!save) {
            throw new BusinessException("app 保存失败！！！");
        }
    }

    private void checkAppNameIsDuplicate(AppSaveAndUpdateRequestDto dto) {
        String appName = dto.getAppName();
        Long appId = dto.getAppId();

        LambdaQueryWrapper<App> wrapper = new LambdaQueryWrapper<>();

        // 为 null 表示新增
        if (Objects.isNull(appId)) {
            wrapper.eq(App::getAppName, appName);
            App one = this.getOne(wrapper);
            if (!Objects.isNull(one)) {
                throw new BusinessException("此模板名(" + appName + ")已存在！！！");
            }
        } else {
            wrapper.eq(App::getAppName, appName)
                    .or().eq(App::getAppId, appId);
            List<App> list = this.list(wrapper);
            if (list.size() > 1) {
                throw new BusinessException("此模板名(" + appName + ")已存在！！！");
            }
        }
    }
}




