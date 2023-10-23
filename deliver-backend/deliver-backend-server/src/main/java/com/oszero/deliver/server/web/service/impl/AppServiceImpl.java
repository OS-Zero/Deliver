package com.oszero.deliver.server.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oszero.deliver.server.model.entity.App;
import com.oszero.deliver.server.web.service.AppService;
import com.oszero.deliver.server.web.mapper.AppMapper;
import org.springframework.stereotype.Service;

/**
 * 针对表【app(渠道应用信息)】的数据库操作 Service 实现
 *
 * @author oszero
 * @version 1.0.0
 */
@Service
public class AppServiceImpl extends ServiceImpl<AppMapper, App>
    implements AppService{

}




