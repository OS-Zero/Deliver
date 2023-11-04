package com.oszero.deliver.server.message.param.ding;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * 钉钉卡片消息内容
 *
 * @author oszero
 * @version 1.0.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DingCardParam extends DingMessageParam {

    private DingCardMessage msg;

    @Data
    public static class DingCardMessage {

        private ActionCardDTO action_card;
        private String msgtype;
    }

    @Data
    public static class ActionCardDTO {

        private BtnJsonListDTO btn_json_list;

        private String single_url;

        private String btn_orientation;

        private String single_title;

        private String markdown;

        private String title;


        @Data
        public static class BtnJsonListDTO {

            private String action_url;

            private String title;
        }
    }
}
