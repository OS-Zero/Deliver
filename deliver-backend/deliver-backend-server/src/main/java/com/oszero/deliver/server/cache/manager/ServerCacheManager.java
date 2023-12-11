package com.oszero.deliver.server.cache.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.oszero.deliver.server.cache.constant.CacheConstant;
import com.oszero.deliver.server.client.ding.DingClient;
import com.oszero.deliver.server.client.feishu.FeiShuClient;
import com.oszero.deliver.server.client.wechat.WeChatClient;
import com.oszero.deliver.server.model.entity.App;
import com.oszero.deliver.server.model.entity.Template;
import com.oszero.deliver.server.model.entity.TemplateApp;
import com.oszero.deliver.server.web.service.AppService;
import com.oszero.deliver.server.web.service.TemplateAppService;
import com.oszero.deliver.server.web.service.TemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * 服务端各缓存管理器
 *
 * @author oszero
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class ServerCacheManager {

    private final TemplateService templateService;
    private final TemplateAppService templateAppService;
    private final AppService appService;

    private final DingClient dingClient;
    private final WeChatClient weChatClient;
    private final FeiShuClient feiShuClient;

    // 缓存建立
    @Cacheable(value = CacheConstant.TEMPLATE_CACHE_NAME, key = "#templateId",
            cacheManager = CacheConstant.REDIS_CACHE_MANAGER)
    public Template getTemplate(Long templateId) {
        return templateService.getById(templateId);
    }

    @Cacheable(value = CacheConstant.TEMPLATE_APP_CACHE_NAME, key = "#templateId",
            cacheManager = CacheConstant.REDIS_CACHE_MANAGER)
    public TemplateApp getTemplateApp(Long templateId) {
        LambdaQueryWrapper<TemplateApp> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TemplateApp::getTemplateId, templateId);
        return templateAppService.getOne(wrapper);
    }

    @Cacheable(value = CacheConstant.APP_CACHE_NAME, key = "#appId",
            cacheManager = CacheConstant.REDIS_CACHE_MANAGER)
    public App getApp(Long appId) {
        return appService.getById(appId);
    }


    // 清空缓存
    @CacheEvict(value = CacheConstant.TEMPLATE_CACHE_NAME, key = "#templateId",
            cacheManager = CacheConstant.REDIS_CACHE_MANAGER)
    public void evictTemplate(Long templateId) {
    }

    @CacheEvict(value = CacheConstant.TEMPLATE_APP_CACHE_NAME, key = "#templateId",
            cacheManager = CacheConstant.REDIS_CACHE_MANAGER)
    public void evictTemplateApp(Long templateId) {
    }

    @CacheEvict(value = CacheConstant.APP_CACHE_NAME, key = "#appId",
            cacheManager = CacheConstant.REDIS_CACHE_MANAGER)
    public void evictApp(Long appId) {
    }

}
