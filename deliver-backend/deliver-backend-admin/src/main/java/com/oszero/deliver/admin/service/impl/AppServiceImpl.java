package com.oszero.deliver.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oszero.deliver.admin.model.dto.request.AppSearchRequestDto;
import com.oszero.deliver.admin.model.entity.App;
import com.oszero.deliver.admin.service.AppService;
import com.oszero.deliver.admin.mapper.AppMapper;
import org.springframework.stereotype.Service;

/**
 * app serviceImpl
 *
 * @author oszero
 * @version 1.0.0
 */
@Service
public class AppServiceImpl extends ServiceImpl<AppMapper, App>
    implements AppService{

    @Override
    public void getAppPagesByCondition(AppSearchRequestDto appSearchRequestDto) {

    }
}




