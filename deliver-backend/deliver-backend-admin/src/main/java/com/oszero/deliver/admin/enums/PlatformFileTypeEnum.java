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
    DING_IMAGE("1-1", "钉钉图片", "image", AppTypeEnum.DING.getCode()),
    DING_VOICE("1-2", "钉钉语音", "voice", AppTypeEnum.DING.getCode()),
    DING_VIDEO("1-3", "钉钉视频", "video", AppTypeEnum.DING.getCode()),
    DING_FILE("1-4", "钉钉文件", "file", AppTypeEnum.DING.getCode()),

    /**
     * 企业微信平台文件 2 代表企业微信
     */
    WECHAT_IMAGE("2-1", "企业微信图片", "image", AppTypeEnum.WECHAT.getCode()),
    WECHAT_VOICE("2-2", "企业微信语音", "voice", AppTypeEnum.WECHAT.getCode()),
    WECHAT_VIDEO("2-3", "企业微信视频", "video", AppTypeEnum.WECHAT.getCode()),
    WECHAT_FILE("2-4", "企业微信普通文件", "file", AppTypeEnum.WECHAT.getCode()),

    /**
     * 飞书平台文件 3 代表飞书
     */
    FEI_SHU_IMAGE("3-1", "飞书图片", "image", AppTypeEnum.FEI_SHU.getCode()),
    FEI_SHU_OPUS("3-2", "飞书 opus 音频文件", "opus", AppTypeEnum.FEI_SHU.getCode()),
    FEI_SHU_MP4("3-3", "飞书 mp4 视频文件", "mp4", AppTypeEnum.FEI_SHU.getCode()),
    FEI_SHU_PDF("3-4", "飞书 pdf 格式文件", "pdf", AppTypeEnum.FEI_SHU.getCode()),
    FEI_SHU_DOC("3-5", "飞书 doc 格式文件", "doc", AppTypeEnum.FEI_SHU.getCode()),
    FEI_SHU_XLS("3-6", "飞书 xls 格式文件", "xls", AppTypeEnum.FEI_SHU.getCode()),
    FEI_SHU_PPT("3-7", "飞书 ppt 格式文件", "ppt", AppTypeEnum.FEI_SHU.getCode()),
    FEI_SHU_STREAM("3-8", "飞书 stream 格式文件", "stream", AppTypeEnum.FEI_SHU.getCode()),
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
