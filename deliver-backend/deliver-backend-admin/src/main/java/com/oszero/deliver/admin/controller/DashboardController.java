package com.oszero.deliver.admin.controller;

import com.oszero.deliver.admin.service.AppService;
import com.oszero.deliver.admin.service.MessageRecordService;
import com.oszero.deliver.admin.service.TemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 仪表盘
 *
 * @author oszero
 * @version 1.0.0
 */
@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final AppService appService;
    private final TemplateService templateService;
    private final MessageRecordService messageRecordService;
}
