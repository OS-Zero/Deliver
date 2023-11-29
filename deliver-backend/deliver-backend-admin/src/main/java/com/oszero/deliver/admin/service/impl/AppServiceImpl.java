package com.oszero.deliver.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oszero.deliver.admin.constant.AppConfigConstant;
import com.oszero.deliver.admin.exception.BusinessException;
import com.oszero.deliver.admin.mapper.AppMapper;
import com.oszero.deliver.admin.model.dto.request.*;
import com.oszero.deliver.admin.model.dto.response.AppByChannelResponseDto;
import com.oszero.deliver.admin.model.dto.response.AppSearchResponseDto;
import com.oszero.deliver.admin.model.entity.App;
import com.oszero.deliver.admin.model.entity.TemplateApp;
import com.oszero.deliver.admin.service.AppService;
import com.oszero.deliver.admin.service.TemplateAppService;
import com.oszero.deliver.admin.util.AesUtils;
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
    private final AesUtils aesUtils;

    @Override
    public Page<AppSearchResponseDto> getAppPagesByCondition(AppSearchRequestDto dto) {
        LambdaQueryWrapper<App> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StrUtil.isNotBlank(dto.getAppName()), App::getAppName, dto.getAppName())
                .eq(!Objects.isNull(dto.getChannelType()), App::getChannelType, dto.getChannelType())
                .gt(!Objects.isNull(dto.getStartTime()), App::getCreateTime, dto.getStartTime())
                .lt(!Objects.isNull(dto.getStartTime()), App::getCreateTime, dto.getEndTime())
                .orderByDesc(App::getCreateTime);

        Page<App> appPage = new Page<>(dto.getCurrentPage(), dto.getPageSize());
        this.page(appPage, wrapper);
        List<AppSearchResponseDto> collect = appPage.getRecords().stream()
                .map(app -> {
                    AppSearchResponseDto appSearchResponseDto = new AppSearchResponseDto();
                    BeanUtil.copyProperties(app, appSearchResponseDto);
                    appSearchResponseDto.setAppConfig(aesUtils.decrypt(app.getAppConfig()));
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
        // 去掉两边空格
        dto.setAppName(dto.getAppName().trim());
        // 去重判断
        checkAppNameIsDuplicate(dto);

        App app = new App();
        BeanUtil.copyProperties(dto, app);
        app.setAppConfig(aesUtils.encrypt(dto.getAppConfig()));
        boolean update = this.updateById(app);
        if (!update) {
            throw new BusinessException("app 更新失败！！！");
        }
    }

    @Override
    public void save(AppSaveAndUpdateRequestDto dto) {
        // 去掉两边空格
        dto.setAppName(dto.getAppName().trim());
        // 去重判断
        checkAppNameIsDuplicate(dto);

        App app = new App();
        BeanUtil.copyProperties(dto, app);
        app.setAppConfig(aesUtils.encrypt(dto.getAppConfig()));
        boolean save = this.save(app);
        if (!save) {
            throw new BusinessException("app 保存失败！！！");
        }
    }

    @Override
    public void updateStatusById(AppUpdateStatusRequestDto dto) {
        App app = new App();
        BeanUtil.copyProperties(dto, app);
        boolean update = this.updateById(app);
        if (!update) {
            throw new BusinessException("app 状态更新失败！！！");
        }
    }

    @Override
    public List<AppByChannelResponseDto> getAppByChannelType(TemplateAddGetByChannelRequestDto dto) {
        Integer channelType = dto.getChannelType();
        LambdaQueryWrapper<App> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(App::getChannelType, channelType)
                .orderByDesc(App::getCreateTime);
        List<App> list = this.list(wrapper);
        return list.stream().map(app -> {
            AppByChannelResponseDto appByChannelResponseDto = new AppByChannelResponseDto();
            BeanUtil.copyProperties(app, appByChannelResponseDto);
            return appByChannelResponseDto;
        }).collect(Collectors.toList());
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
                throw new BusinessException("此 APP 名(" + appName + ")已存在！！！");
            }
        } else {
            wrapper.eq(App::getAppName, appName)
                    .or().eq(App::getAppId, appId);
            List<App> list = this.list(wrapper);
            if (list.size() > 1) {
                throw new BusinessException("此 APP 名(" + appName + ")已存在！！！");
            }
        }
    }

    @Override
    public String getAppConfigByChannelType(AppConfigByChannelRequestDto dto) {
        Integer channelType = dto.getChannelType();
        switch (channelType) {
            case 1: {
                return AppConfigConstant.CALL_CONFIG;
            }
            case 2: {
                return AppConfigConstant.SMS_CONFIG;
            }
            case 3: {
                return AppConfigConstant.MAIL_CONFIG;
            }
            case 4: {
                return AppConfigConstant.DING_CONFIG;
            }
            case 5: {
                return AppConfigConstant.WECHAT_CONFIG;
            }
            case 6: {
                return AppConfigConstant.FEI_SHU_CONFIG;
            }
        }
        return "{}";
    }
}




