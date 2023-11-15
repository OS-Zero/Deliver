package com.oszero.deliver.admin.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 平台文件类型枚举
 *
 * @author oszero
 * @version 1.0.0
 */
@Getter
@ToString
@AllArgsConstructor
public enum PlatformFileTypeEnum {

    /**
     * 钉钉平台文件 1 代表钉钉
     */

    /**
     * 企业微信平台文件 2 代表企业微信
     */

    /**
     * 飞书平台文件 3 代表飞书
     */
    IMAGE("3-1", "图片", "image", 3),
    OPUS("3-2", "opus音频文件", "opus", 3),
    MP4("3-3", "mp4视频文件", "mp4", 3),
    PDF("3-4", "pdf格式文件", "pdf", 3),
    DOC("3-5", "doc格式文件", "doc", 3),
    XLS("3-6", "xls格式文件", "xls", 3),
    PPT("3-7", "ppt格式文件", "ppt", 3),
    STREAM("3-8", "stream格式文件", "stream", 3),
    ;

    private final String code;
    private final String name;
    private final String fileType;
    private final Integer appType;

    public static PlatformFileTypeEnum getInstanceByCode(String code) {
        for (PlatformFileTypeEnum v : values()) {
            if (v.getCode().equals(code)) {
                return v;
            }
        }
        return null;
    }
}
