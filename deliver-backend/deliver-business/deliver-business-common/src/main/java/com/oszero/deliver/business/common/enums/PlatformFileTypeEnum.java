/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.oszero.deliver.business.common.enums;

import cn.hutool.core.util.StrUtil;
import com.oszero.deliver.business.common.constant.CommonConstant;
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
     * 钉钉平台文件
     */
    DING_IMAGE(getPlatformFileTypeEnumCode(ChannelTypeEnum.DING, newDingIndex()), "钉钉图片", "image", ChannelTypeEnum.DING.getCode()),
    DING_VOICE(getPlatformFileTypeEnumCode(ChannelTypeEnum.DING, newDingIndex()), "钉钉语音", "voice", ChannelTypeEnum.DING.getCode()),
    DING_VIDEO(getPlatformFileTypeEnumCode(ChannelTypeEnum.DING, newDingIndex()), "钉钉视频", "video", ChannelTypeEnum.DING.getCode()),
    DING_FILE(getPlatformFileTypeEnumCode(ChannelTypeEnum.DING, newDingIndex()), "钉钉文件", "file", ChannelTypeEnum.DING.getCode()),

    /**
     * 企业微信平台文件
     */
    WECHAT_IMAGE(getPlatformFileTypeEnumCode(ChannelTypeEnum.WECHAT, newWechatIndex()), "企业微信图片", "image", ChannelTypeEnum.WECHAT.getCode()),
    WECHAT_VOICE(getPlatformFileTypeEnumCode(ChannelTypeEnum.WECHAT, newWechatIndex()), "企业微信语音", "voice", ChannelTypeEnum.WECHAT.getCode()),
    WECHAT_VIDEO(getPlatformFileTypeEnumCode(ChannelTypeEnum.WECHAT, newWechatIndex()), "企业微信视频", "video", ChannelTypeEnum.WECHAT.getCode()),
    WECHAT_FILE(getPlatformFileTypeEnumCode(ChannelTypeEnum.WECHAT, newWechatIndex()), "企业微信普通文件", "file", ChannelTypeEnum.WECHAT.getCode()),

    /**
     * 飞书平台文件
     */
    FEI_SHU_IMAGE(getPlatformFileTypeEnumCode(ChannelTypeEnum.FEI_SHU, newFeiShuIndex()), "飞书图片", "image", ChannelTypeEnum.FEI_SHU.getCode()),
    FEI_SHU_OPUS(getPlatformFileTypeEnumCode(ChannelTypeEnum.FEI_SHU, newFeiShuIndex()), "飞书 opus 音频文件", "opus", ChannelTypeEnum.FEI_SHU.getCode()),
    FEI_SHU_MP4(getPlatformFileTypeEnumCode(ChannelTypeEnum.FEI_SHU, newFeiShuIndex()), "飞书 mp4 视频文件", "mp4", ChannelTypeEnum.FEI_SHU.getCode()),
    FEI_SHU_PDF(getPlatformFileTypeEnumCode(ChannelTypeEnum.FEI_SHU, newFeiShuIndex()), "飞书 pdf 格式文件", "pdf", ChannelTypeEnum.FEI_SHU.getCode()),
    FEI_SHU_DOC(getPlatformFileTypeEnumCode(ChannelTypeEnum.FEI_SHU, newFeiShuIndex()), "飞书 doc 格式文件", "doc", ChannelTypeEnum.FEI_SHU.getCode()),
    FEI_SHU_XLS(getPlatformFileTypeEnumCode(ChannelTypeEnum.FEI_SHU, newFeiShuIndex()), "飞书 xls 格式文件", "xls", ChannelTypeEnum.FEI_SHU.getCode()),
    FEI_SHU_PPT(getPlatformFileTypeEnumCode(ChannelTypeEnum.FEI_SHU, newFeiShuIndex()), "飞书 ppt 格式文件", "ppt", ChannelTypeEnum.FEI_SHU.getCode()),
    FEI_SHU_STREAM(getPlatformFileTypeEnumCode(ChannelTypeEnum.FEI_SHU, newFeiShuIndex()), "飞书 stream 格式文件", "stream", ChannelTypeEnum.FEI_SHU.getCode());

    private static int dingIndex = 0;
    private static int wechatIndex = 0;
    private static int feiShuIndex = 0;

    private static String newDingIndex() {
        return String.valueOf(++dingIndex);
    }

    private static String newWechatIndex() {
        return String.valueOf(++wechatIndex);
    }

    private static String newFeiShuIndex() {
        return String.valueOf(++feiShuIndex);
    }

    private static String getPlatformFileTypeEnumCode(ChannelTypeEnum channelTypeEnum, String newIndex) {
        return channelTypeEnum.getCode() + CommonConstant.CODE_SEPARATE + newIndex;
    }

    private final String code;
    private final String name;
    private final String fileType;
    private final String channelType;

    public static PlatformFileTypeEnum getInstanceByCode(String code) {
        for (PlatformFileTypeEnum typeEnum : values()) {
            if (StrUtil.equals(typeEnum.getCode(), code)) {
                return typeEnum;
            }
        }
        return null;
    }
}
