package com.oszero.deliver.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oszero.deliver.admin.model.entity.App;
import com.oszero.deliver.admin.service.AppService;
import com.oszero.deliver.admin.mapper.AppMapper;
import org.springframework.stereotype.Service;

/**
* @author 23088
* @description 针对表【app(渠道应用信息)】的数据库操作Service实现
* @createDate 2023-10-13 21:53:14
*/
@Service
public class AppServiceImpl extends ServiceImpl<AppMapper, App>
    implements AppService{

}




