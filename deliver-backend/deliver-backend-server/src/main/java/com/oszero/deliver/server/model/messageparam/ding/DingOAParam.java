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

package com.oszero.deliver.server.model.messageparam.ding;

import lombok.*;

import java.util.List;

/**
 * 钉钉OA消息内容
 *
 * @author oszero
 * @version 1.0.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DingOAParam extends DingMessageParam {

    private OAMessageDTO msg;

    @Data
    public static class OAMessageDTO {

        private String msgtype;

        private OaDTO oa;
    }

    @Data
    public static class OaDTO {

        private String message_url;

        private HeadDTO head;

        private BodyDTO body;

        @Data
        public static class HeadDTO {

            private String bgcolor;

            private String text;
        }

        @Data
        public static class BodyDTO {

            private String title;

            private List<FormDTO> form;

            private RichDTO rich;

            private String content;

            private String image;

            private String file_count;

            private String author;

            @Data
            public static class RichDTO {

                private String num;

                private String unit;
            }

            @Data
            public static class FormDTO {

                private String key;

                private String value;
            }
        }
    }
}
