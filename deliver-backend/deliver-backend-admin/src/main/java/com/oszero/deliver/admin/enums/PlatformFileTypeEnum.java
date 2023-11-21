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
    DING_IMAGE("1-1", "图片", "image", 1),
    DING_VOICE("1-2", "语音", "voice", 1),
    DING_VIDEO("1-3", "视频", "video", 1),
    DING_FILE("1-4", "文件", "file", 1),

//    /**
//     * 企业微信平台文件 2 代表企业微信
//     */

    /**
     * 飞书平台文件 3 代表飞书
     */
    FEI_SHU_IMAGE("3-1", "图片", "image", 3),
    FEI_SHU_OPUS("3-2", "opus音频文件", "opus", 3),
    FEI_SHU_MP4("3-3", "mp4视频文件", "mp4", 3),
    FEI_SHU_PDF("3-4", "pdf格式文件", "pdf", 3),
    FEI_SHU_DOC("3-5", "doc格式文件", "doc", 3),
    FEI_SHU_XLS("3-6", "xls格式文件", "xls", 3),
    FEI_SHU_PPT("3-7", "ppt格式文件", "ppt", 3),
    FEI_SHU_STREAM("3-8", "stream格式文件", "stream", 3),
    ;

    private final String code;
    private final String name;
    private final String fileType;
    private final Integer appType;

    /**
     * 通过 code 获取实例
     *
     * @param code code 码
     * @return 实例
     */
    public static PlatformFileTypeEnum getInstanceByCode(String code) {
        for (PlatformFileTypeEnum v : values()) {
            if (v.getCode().equals(code)) {
                return v;
            }
        }
        return null;
    }
}
