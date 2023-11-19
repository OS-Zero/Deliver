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
    Set<String> feiShuImageFormatSet =
            new HashSet<>(Arrays.asList("jpeg", "png", "webp", "gif", "tiff", "bmp", "ico"));
    Set<String> feiShuFileFormatSet =
            new HashSet<>(Arrays.asList("opus", "mp4", "pdf", "doc", "xls", "ppt", "stream"));
    Long feiShuImageFileMaxSize = 10L * 1024 * 1024;
    Long feiShuFileMaxSize = 30L * 1024 * 1024;

    Set<String> dingFileFormatSet =
            new HashSet<>(Arrays.asList("jpg","gif","png","bmp","amr","mp3","wav","mp4",
                    "doc","docx","xls","xlsx","ppt","pptx","zip","pdf","rar"));

    Long dingVoiceMaxSize = 2L * 1024 *1024;
    Long dingFileMaxSize = 20L * 1024 * 1024;
}
