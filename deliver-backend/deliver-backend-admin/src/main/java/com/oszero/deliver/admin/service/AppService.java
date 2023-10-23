package com.oszero.deliver.admin.service;

import com.oszero.deliver.admin.model.dto.request.AppSearchRequestDto;
import com.oszero.deliver.admin.model.entity.App;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 23088
* @description 针对表【app(渠道应用信息)】的数据库操作Service
* @createDate 2023-10-13 21:53:14
*/
public interface AppService extends IService<App> {

    void getAppPagesByCondition(AppSearchRequestDto appSearchRequestDto);
}
