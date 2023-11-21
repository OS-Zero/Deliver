package com.oszero.deliver.admin.constant;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 平台文件常量
 *
 * @author oszero
 * @version 1.0.0
 */
public interface PlatformFileConstant {
    Set<String> FEI_SHU_IMAGE_FORMAT_SET =
            new HashSet<>(Arrays.asList("jpeg", "png", "webp", "gif", "tiff", "bmp", "ico"));

    Set<String> FEI_SHU_FILE_FORMAT_SET =
            new HashSet<>(Arrays.asList("opus", "mp4", "pdf", "doc", "xls", "ppt", "stream"));

    Long FEI_SHU_IMAGE_FILE_MAX_SIZE = 10L * 1024 * 1024;

    Long FEI_SHU_FILE_MAX_SIZE = 30L * 1024 * 1024;

    Set<String> DING_FILE_FORMAT_SET =
            new HashSet<>(Arrays.asList("jpg", "gif", "png", "bmp", "amr", "mp3", "wav", "mp4",
                    "doc", "docx", "xls", "xlsx", "ppt", "pptx", "zip", "pdf", "rar"));

    Long DING_VOICE_MAX_SIZE = 2L * 1024 * 1024;

    Long DING_FILE_MAX_SIZE = 20L * 1024 * 1024;
}
