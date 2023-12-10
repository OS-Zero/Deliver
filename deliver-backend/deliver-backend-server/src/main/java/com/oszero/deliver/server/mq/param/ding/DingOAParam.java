package com.oszero.deliver.server.mq.param.ding;

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
