package com.oszero.deliver.server.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oszero.deliver.server.model.entity.Template;
import com.oszero.deliver.server.web.service.TemplateService;
import com.oszero.deliver.server.web.mapper.TemplateMapper;
import org.springframework.stereotype.Service;

/**
 * 针对表【template(消息模板)】的数据库操作 Service 实现
 *
 * @author oszero
 * @version 1.0.0
 */
@Service
public class TemplateServiceImpl extends ServiceImpl<TemplateMapper, Template>
    implements TemplateService{

}




