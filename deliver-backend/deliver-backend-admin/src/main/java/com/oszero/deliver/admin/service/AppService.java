package com.oszero.deliver.admin.service;

import com.oszero.deliver.admin.model.dto.request.AppSearchRequestDto;
import com.oszero.deliver.admin.model.entity.App;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * app service
 *
 * @author oszero
 * @version 1.0.0
 */
public interface AppService extends IService<App> {

    void getAppPagesByCondition(AppSearchRequestDto appSearchRequestDto);
}
