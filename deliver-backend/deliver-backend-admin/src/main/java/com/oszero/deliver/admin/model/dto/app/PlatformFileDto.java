package com.oszero.deliver.admin.model.dto.app;

import lombok.Data;

@Data
public class PlatformFileDto {
    private String fileName;
    private String fileType;
    private byte[] file;
}
