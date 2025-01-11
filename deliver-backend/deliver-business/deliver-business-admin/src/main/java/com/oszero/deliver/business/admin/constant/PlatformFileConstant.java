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

package com.oszero.deliver.business.admin.constant;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author oszero
 * @version 1.0.0
 */
public interface PlatformFileConstant {

    /********** 飞书 **********/
    Set<String> FEI_SHU_IMAGE_FORMAT_SET =
            new HashSet<>(Arrays.asList("jpeg", "png", "webp", "gif", "tiff", "bmp", "ico"));
    Set<String> FEI_SHU_FILE_FORMAT_SET =
            new HashSet<>(Arrays.asList("opus", "mp4", "pdf", "doc", "xls", "ppt", "stream", "xlsx"));
    Long FEI_SHU_IMAGE_FILE_MAX_SIZE = 10L * 1024 * 1024;
    Long FEI_SHU_FILE_MAX_SIZE = 30L * 1024 * 1024;

    /********** 钉钉 **********/
    Set<String> DING_FILE_FORMAT_SET =
            new HashSet<>(Arrays.asList("jpg", "gif", "png", "bmp", "amr", "mp3", "wav", "mp4",
                    "doc", "docx", "xls", "xlsx", "ppt", "pptx", "zip", "pdf", "rar"));
    Long DING_FILE_MAX_SIZE = 20L * 1024 * 1024;
    Long DING_VOICE_MAX_SIZE = 2L * 1024 * 1024;

    /********** 企微 **********/
    Set<String> WECHAT_IMAGE_FORMAT_SET =
            new HashSet<>(Arrays.asList("jpg", "png"));
    Set<String> WECHAT_VOICE_FORMAT_SET =
            new HashSet<>(Collections.singletonList("amr"));
    Set<String> WECHAT_VIDEO_FORMAT_SET =
            new HashSet<>(Collections.singletonList("mp4"));
    Set<String> WECHAT_FILE_FORMAT_SET =
            new HashSet<>(Collections.emptyList());
    Long WECHAT_IMAGE_MAX_SIZE = 10L * 1024 * 1024;
    Long WECHAT_VOICE_MAX_SIZE = 2L * 1024 * 1024;
    Long WECHAT_VIDEO_MAX_SIZE = 10L * 1024 * 1024;
    Long WECHAT_FILE_MAX_SIZE = 20L * 1024 * 1024;
}
