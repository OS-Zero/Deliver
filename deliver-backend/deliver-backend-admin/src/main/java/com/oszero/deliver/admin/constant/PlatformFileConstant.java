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

package com.oszero.deliver.admin.constant;

import com.oszero.deliver.admin.enums.PlatformFileTypeEnum;

import java.util.*;

/**
 * 平台文件常量
 *
 * @author oszero
 * @version 1.0.0
 */
public class PlatformFileConstant {

    /**
     * 飞书
     */
    public static final Set<String> FEI_SHU_IMAGE_FORMAT_SET =
            new HashSet<>(Arrays.asList("jpeg", "png", "webp", "gif", "tiff", "bmp", "ico"));
    public static final Set<String> FEI_SHU_FILE_FORMAT_SET =
            new HashSet<>(Arrays.asList("opus", "mp4", "pdf", "doc", "xls", "ppt", "stream"));
    public static final Long FEI_SHU_IMAGE_FILE_MAX_SIZE = 10L * 1024 * 1024;
    public static final Long FEI_SHU_FILE_MAX_SIZE = 30L * 1024 * 1024;

    /**
     * 钉钉
     */
    public static final Set<String> DING_FILE_FORMAT_SET =
            new HashSet<>(Arrays.asList("jpg", "gif", "png", "bmp", "amr", "mp3", "wav", "mp4",
                    "doc", "docx", "xls", "xlsx", "ppt", "pptx", "zip", "pdf", "rar"));
    public static final Long DING_FILE_MAX_SIZE = 20L * 1024 * 1024;
    public static final Long DING_VOICE_MAX_SIZE = 2L * 1024 * 1024;

    /**
     * 企微
     */
    public static final Set<String> WECHAT_IMAGE_FORMAT_SET =
            new HashSet<>(Arrays.asList("jpg", "png"));
    public static final Set<String> WECHAT_VOICE_FORMAT_SET =
            new HashSet<>(Collections.singletonList("amr"));
    public static final Set<String> WECHAT_VIDEO_FORMAT_SET =
            new HashSet<>(Collections.singletonList("mp4"));
    public static final Set<String> WECHAT_FILE_FORMAT_SET =
            new HashSet<>(Collections.emptyList());
    public static final Long WECHAT_IMAGE_MAX_SIZE = 10L * 1024 * 1024;
    public static final Long WECHAT_VOICE_MAX_SIZE = 2L * 1024 * 1024;
    public static final Long WECHAT_VIDEO_MAX_SIZE = 10L * 1024 * 1024;
    public static final Long WECHAT_FILE_MAX_SIZE = 20L * 1024 * 1024;

    public static final Map<String, String> FILE_TYPE_NAME_MAP = new HashMap<>();

    static {
        FILE_TYPE_NAME_MAP.put("1-image", PlatformFileTypeEnum.DING_IMAGE.getName());
        FILE_TYPE_NAME_MAP.put("1-voice", PlatformFileTypeEnum.DING_VOICE.getName());
        FILE_TYPE_NAME_MAP.put("1-video", PlatformFileTypeEnum.DING_VIDEO.getName());
        FILE_TYPE_NAME_MAP.put("1-file", PlatformFileTypeEnum.DING_FILE.getName());

        FILE_TYPE_NAME_MAP.put("2-image", PlatformFileTypeEnum.WECHAT_IMAGE.getName());
        FILE_TYPE_NAME_MAP.put("2-voice", PlatformFileTypeEnum.WECHAT_VOICE.getName());
        FILE_TYPE_NAME_MAP.put("2-video", PlatformFileTypeEnum.WECHAT_VIDEO.getName());
        FILE_TYPE_NAME_MAP.put("2-file", PlatformFileTypeEnum.WECHAT_FILE.getName());

        FILE_TYPE_NAME_MAP.put("3-image", PlatformFileTypeEnum.FEI_SHU_IMAGE.getName());
        FILE_TYPE_NAME_MAP.put("3-opus", PlatformFileTypeEnum.FEI_SHU_OPUS.getName());
        FILE_TYPE_NAME_MAP.put("3-mp4", PlatformFileTypeEnum.FEI_SHU_MP4.getName());
        FILE_TYPE_NAME_MAP.put("3-pdf", PlatformFileTypeEnum.FEI_SHU_PDF.getName());
        FILE_TYPE_NAME_MAP.put("3-doc", PlatformFileTypeEnum.FEI_SHU_DOC.getName());
        FILE_TYPE_NAME_MAP.put("3-xls", PlatformFileTypeEnum.FEI_SHU_XLS.getName());
        FILE_TYPE_NAME_MAP.put("3-ppt", PlatformFileTypeEnum.FEI_SHU_PPT.getName());
        FILE_TYPE_NAME_MAP.put("3-stream", PlatformFileTypeEnum.FEI_SHU_STREAM.getName());
    }
}
