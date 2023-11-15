package com.oszero.deliver.admin.controller;

import com.oszero.deliver.admin.service.PlatformFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/platformFile")
@RequiredArgsConstructor
public class PlatformFileController {
    private final PlatformFileService platformFileService;
}
