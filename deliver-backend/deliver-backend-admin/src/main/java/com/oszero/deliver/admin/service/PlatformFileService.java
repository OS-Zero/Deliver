package com.oszero.deliver.admin.service;

import com.oszero.deliver.admin.model.dto.request.PlatformFileUploadRequestDto;
import com.oszero.deliver.admin.model.entity.PlatformFile;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
* @author 23088
* @description 针对表【platform_file(平台文件表)】的数据库操作Service
* @createDate 2023-11-14 19:38:02
*/
public interface PlatformFileService extends IService<PlatformFile> {

    void uploadFile(PlatformFileUploadRequestDto dto, MultipartFile platformFile) throws IOException;
}
