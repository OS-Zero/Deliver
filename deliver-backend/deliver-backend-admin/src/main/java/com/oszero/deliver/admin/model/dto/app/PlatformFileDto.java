package com.oszero.deliver.admin.model.dto.app;

import lombok.Data;

/**
 * 平台文件 dto
 *
 * @author oszero
 * @version 1.0.0
 */
@Data
public class PlatformFileDto {
    private String fileName;
    private String fileType;
    private byte[] file;
}
