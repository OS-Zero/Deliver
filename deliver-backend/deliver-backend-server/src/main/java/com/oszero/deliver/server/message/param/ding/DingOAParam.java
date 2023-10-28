package com.oszero.deliver.server.message.param.ding;


import com.fasterxml.jackson.annotation.JsonProperty;
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


    private String msgtype;

    private OaDTO oa;


    public static class OaDTO {

        private String message_url;

        private HeadDTO head;

        private BodyDTO body;


        public static class HeadDTO {

            private String bgcolor;

            private String text;
        }


        public static class BodyDTO {

            private String title;

            private List<FormDTO> form;

            private RichDTO rich;

            private String content;

            private String image;

            private String file_count;

            private String author;


            public static class RichDTO {

                private String num;

                private String unit;
            }


            public static class FormDTO {

                private String key;

                private String value;
            }
        }
    }
}
