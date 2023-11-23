package com.oszero.deliver.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.oszero.deliver.admin.model.dto.request.PlatformFileSearchRequestDto;
import com.oszero.deliver.admin.model.dto.request.PlatformFileUploadRequestDto;
import com.oszero.deliver.admin.model.dto.response.PlatformFileSearchResponseDto;
import com.oszero.deliver.admin.model.entity.PlatformFile;

import java.io.IOException;

/**
 * @author 23088
 * @description 针对表【platform_file(平台文件表)】的数据库操作Service
 * @createDate 2023-11-14 19:38:02
 */
public interface PlatformFileService extends IService<PlatformFile> {

    /**
     * 上传平台文件
     *
     * @param dto 参数
     * @throws IOException 异常
     */
    void uploadFile(PlatformFileUploadRequestDto dto) throws IOException;

    /**
     * 分页搜索平台文件
     *
     * @param dto 参数
     * @return Page
     */
    Page<PlatformFileSearchResponseDto> getPagePlatformFile(PlatformFileSearchRequestDto dto);
}
